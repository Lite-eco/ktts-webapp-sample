package com.kttswebapptemplate.controller

import com.kttswebapptemplate.config.SafeSessionRepository
import com.kttswebapptemplate.controller.RemoteController.Companion.remoteRoute
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.Session as TemplateSampleSession
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.repository.user.UserSessionLogDao
import com.kttswebapptemplate.service.user.UserSessionService
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.TransactionIsolationService
import mu.KotlinLogging
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.session.Session as SpringSession
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(remoteRoute)
class RemoteController(
    @Value("\${remote-controller.expected-secu}") private val expectedSecu: String,
    private val jooq: DSLContext,
    private val userDao: UserDao,
    private val userSessionLogDao: UserSessionLogDao,
    private val sessionRepository: SafeSessionRepository,
    private val userSessionService: UserSessionService,
    private val transactionIsolationService: TransactionIsolationService,
    private val dateService: DateService
) {

    val logger = KotlinLogging.logger {}

    companion object {
        const val remoteRoute = "/remote"
        private val SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT"
    }

    private fun checkSecu(secu: String) {
        if (secu != expectedSecu) {
            throw IllegalArgumentException("missing secu")
        }
    }

    @PostMapping("/update-sessions")
    fun updateSessions(@RequestParam secu: String) =
        synchronized(this) {
            checkSecu(secu)
            jooq.connection { connection ->
                val sessionIds =
                    connection.createStatement().use {
                        val r = it.executeQuery("select session_id from spring_session")
                        jooq.fetch(r).map { it.getValue("session_id") as String }
                    }
                sessionIds
                    .mapNotNull { sessionRepository.findById(it) }
                    .forEach { session ->
                        val context =
                            session.getAttribute<SecurityContextImpl>(SPRING_SECURITY_CONTEXT)
                        val conversion =
                            ((context.authentication as UsernamePasswordAuthenticationToken)
                                    .principal as TemplateSampleSession)
                                .let { userSessionService.convert(it) }
                        if (conversion.needsUpdate) {
                            updateSession(session, conversion.session)
                        }
                    }
            }
        }

    @PostMapping("/add-role")
    fun addUserRole(
        @RequestParam secu: String,
        @RequestParam email: String,
        @RequestParam role: Role
    ) =
        synchronized(this) {
            checkSecu(secu)
            val user = userDao.fetchByMail(email)
            val roles = user.roles + role
            userDao.updateRoles(user.id, roles, dateService.now())
            userSessionLogDao.fetchIdsByUserId(user.id).forEach { sessionId ->
                val userSession = UserSession(sessionId, user.id, roles)
                val userSessionPrincipalName = userSession.toString()
                sessionRepository.findByPrincipalName(userSessionPrincipalName).values.forEach {
                    updateSession(it, userSession)
                }
            }
        }

    private fun updateSession(session: SpringSession, userSession: UserSession) {
        logger.info { "Save up-to-date session ${session.id}" }
        val springAuthentication = UsernamePasswordAuthenticationToken(userSession, null, null)
        val context = session.getAttribute<SecurityContextImpl>(SPRING_SECURITY_CONTEXT)
        context.authentication = springAuthentication
        session.setAttribute(SPRING_SECURITY_CONTEXT, context)
        transactionIsolationService.execute { sessionRepository.save(session) }
    }
}