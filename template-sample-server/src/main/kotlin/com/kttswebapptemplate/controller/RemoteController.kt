package com.kttswebapptemplate.controller

import com.kttswebapptemplate.config.SafeSessionRepository
import com.kttswebapptemplate.controller.RemoteController.Companion.remoteRoute
import com.kttswebapptemplate.domain.Session as TemplateSampleSession
import com.kttswebapptemplate.service.user.UserService
import com.kttswebapptemplate.service.user.UserSessionService
import mu.KotlinLogging
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(remoteRoute)
class RemoteController(
    @Value("\${remote-controller.expected-secu}") private val expectedSecu: String,
    private val jooq: DSLContext,
    private val sessionRepository: SafeSessionRepository,
    private val userSessionService: UserSessionService,
    private val userService: UserService,
) {

    val logger = KotlinLogging.logger {}

    companion object {
        const val remoteRoute = "/remote"
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
                            session.getAttribute<SecurityContextImpl>(
                                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)
                        val conversion =
                            ((context.authentication as UsernamePasswordAuthenticationToken)
                                    .principal as TemplateSampleSession)
                                .let { userSessionService.convert(it) }
                        if (conversion.needsUpdate) {
                            userService.updateSession(session, conversion.session)
                        }
                    }
            }
        }
}
