package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.Session
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.domain.UserSessionId
import com.kttswebapptemplate.error.AppErrors
import com.kttswebapptemplate.error.TemplateSampleSecurityException
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.repository.user.UserSessionLogDao
import com.kttswebapptemplate.service.utils.ApplicationInstance
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.random.RandomService
import java.time.Duration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.stereotype.Service

@Service
class UserSessionService(
    private val cookieCsrfTokenRepository: CookieCsrfTokenRepository,
    private val userDao: UserDao,
    private val userSessionLogDao: UserSessionLogDao,
    private val dateService: DateService,
    private val randomService: RandomService
) {

    val logger = KotlinLogging.logger {}

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

        val userSession = UserSession(sessionId, user.id, user.roles)
        val springAuthentication = UsernamePasswordAuthenticationToken(userSession, null, null)
        SecurityContextHolder.getContext().authentication = springAuthentication

        val csrfToken = cookieCsrfTokenRepository.generateToken(request)
        cookieCsrfTokenRepository.saveToken(csrfToken, request, response)
    }

    fun isAuthenticated() =
        SecurityContextHolder.getContext().authentication.let {
            it != null && it !is AnonymousAuthenticationToken && it.isAuthenticated
        }

    fun hasRole(role: Role): Boolean =
        if (!isAuthenticated()) {
            false
        } else {
            role in getUserSession().roles
        }

    fun verifyRoleOrFail(role: Role?, logIp: String, logClass: Class<Any>) {
        if (role != null) {
            if (!isAuthenticated()) {
                throw TemplateSampleSecurityException("$logIp ${logClass.simpleName}")
            }
            val userSession = getUserSession()
            if (role !in userSession.roles) {
                throw TemplateSampleSecurityException("$userSession $logIp ${logClass.simpleName}")
            }
        }
    }

    fun getUserSession(): UserSession =
        SecurityContextHolder.getContext().authentication.principal.let {
            when (it) {
                // [doc] allows UserSession object evolution without breaking existing sessions
                is Session -> convert(it).session
                // TODO[tmpl][secu] do 403 if anonymousUser
                is AnonymousAuthenticationToken -> throw AppErrors.NotConnectedUser()
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
        //        UserSession(s.sessionId, s.userId, user.roles)
        //    )
        // }
        }

    fun updateCurrentSession(userSession: UserSession): UserSession {
        val springAuthentication = UsernamePasswordAuthenticationToken(userSession, null, null)
        // [doc] this does save session in database
        SecurityContextHolder.getContext().authentication = springAuthentication
        return userSession
    }
}
