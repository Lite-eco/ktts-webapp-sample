package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.config.SafeSessionRepository
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.Session
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.domain.UserSessionId
import com.kttswebapptemplate.domain.UserStatus
import com.kttswebapptemplate.error.AppErrors
import com.kttswebapptemplate.error.TemplateSampleSecurityException
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.repository.user.UserSessionLogDao
import com.kttswebapptemplate.service.utils.ApplicationInstance
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.TransactionIsolationService
import com.kttswebapptemplate.service.utils.random.RandomService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.Duration
import mu.KotlinLogging
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler
import org.springframework.session.Session as SpringSession
import org.springframework.stereotype.Service

@Service
class UserSessionService(
    private val securityContextRepository: HttpSessionSecurityContextRepository,
    private val cookieCsrfTokenRepository: CookieCsrfTokenRepository,
    private val userSessionLogDao: UserSessionLogDao,
    private val dateService: DateService,
    private val randomService: RandomService,
    private val xorCsrfTokenRequestAttributeHandler: XorCsrfTokenRequestAttributeHandler,
    private val transactionIsolationService: TransactionIsolationService,
    private val sessionRepository: SafeSessionRepository,
) {

    val logger = KotlinLogging.logger {}

    companion object {
        const val anonymousUser = "anonymousUser"
    }

    data class SessionConvertion(val needsUpdate: Boolean, val session: UserSession)

    // TODO[tmpl][secu] ?
    val sessionDuration = Duration.ofDays(100)

    // TODO[tmpl][secu] change cookie ?
    fun authenticateUser(
        user: UserDao.Record,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val sessionId = randomService.id<UserSessionId>()

        // create the session if doesn't exist
        val session = request.getSession(true)
        session.maxInactiveInterval = sessionDuration.seconds.toInt()

        val now = dateService.now()
        val ip = request.remoteAddr
        userSessionLogDao.insert(
            UserSessionLogDao.Record(
                sessionId, session.id, user.id, ApplicationInstance.deploymentLogId, now, ip))

        val userSession = UserSession(sessionId, user.id, user.status, user.role)
        val springAuthentication = UsernamePasswordAuthenticationToken(userSession, null, null)
        SecurityContextHolder.getContext().let {
            it.authentication = springAuthentication
            securityContextRepository.saveContext(it, request, response)
        }

        cookieCsrfTokenRepository.generateToken(request).let { token ->
            cookieCsrfTokenRepository.saveToken(token, request, response)
            // [doc] we need to manually ask for the "XorCsrfToken"
            xorCsrfTokenRequestAttributeHandler.handle(request, response) { token }
        }
    }

    fun isAuthenticated() =
        SecurityContextHolder.getContext().authentication.let {
            it != null && it !is AnonymousAuthenticationToken && it.isAuthenticated
        }

    fun verifyStatusAndRole(expectedRole: Role?, logIp: String, logClass: Class<Any>) {
        val userSession = getUserSessionIfAuthenticated()
        // one could argue that a loggedin user has the same rights as an anonymous, but handlers
        // could actually make a difference between both
        if (userSession?.status == UserStatus.Disabled) {
            throw TemplateSampleSecurityException("$logIp ${logClass.simpleName}")
        }
        if (expectedRole != null) {
            if (userSession == null) {
                throw TemplateSampleSecurityException("$logIp ${logClass.simpleName}")
            }
            if (userSession.status != UserStatus.Active) {
                throw TemplateSampleSecurityException("$logIp ${logClass.simpleName}")
            }
            when (expectedRole) {
                Role.User -> {}
                Role.Admin ->
                    if (userSession.role != Role.Admin) {
                        throw TemplateSampleSecurityException(
                            "$userSession $logIp ${logClass.simpleName}")
                    }
            }
        }
    }

    fun getUserSessionIfAuthenticated() = if (isAuthenticated()) getUserSession() else null

    fun getUserSession(): UserSession =
        SecurityContextHolder.getContext().authentication.principal.let {
            when (it) {
                // [doc] allows UserSession object evolution without breaking existing sessions
                // & then securityContextRepository.saveContext()
                is Session -> convert(it).session
                anonymousUser -> throw AppErrors.NotConnectedUser()
                // TODO[tmpl][secu] log ?
                else -> throw IllegalStateException("Unexpected principal type ${it.javaClass} $it")
            }
        }

    fun convert(s: Session): SessionConvertion =
        when (s) {
            is UserSession -> SessionConvertion(false, s)
        // [doc] to update a session:
        // is FormerUserSession -> {
        //    logger.info { "Converting session $s" }
        //    val user = userDao.fetch(s.userId) ?: throw IllegalStateException("$s")
        //    SessionConvertion(
        //        true,
        //        UserSession(s.sessionId, s.userId, user.status, user.role)
        //    )
        // }
        }

    fun updateAndPersistAllExistingSessionsForUser(newSession: Session) {
        sessionRepository
            .findByPrincipalName(
                // toString() here is ok cause Spring does it
                newSession.toString())
            .values
            .forEach { updateAndPersistSession(it, newSession) }
    }

    fun updateAndPersistSession(springSession: SpringSession, userSession: Session) {
        logger.info { "Save up-to-date session ${springSession.id}" }
        val springAuthentication = UsernamePasswordAuthenticationToken(userSession, null, null)
        val context =
            springSession.getAttribute<SecurityContextImpl>(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)
        context.authentication = springAuthentication
        springSession.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context)
        transactionIsolationService.execute { sessionRepository.save(springSession) }
    }
}
