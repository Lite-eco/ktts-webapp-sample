package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.UserSession
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

interface CommandHandler<C : Command, R : CommandResponse> {
    fun handle(
        command: C,
        userSession: UserSession?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): R

    abstract class Handler<C : Command, R : CommandResponse> : CommandHandler<C, R> {
        override fun handle(
            command: C,
            userSession: UserSession?,
            request: HttpServletRequest,
            response: HttpServletResponse
        ): R = handle(command)

        abstract fun handle(command: C): R
    }

    abstract class SessionHandler<C : Command, R : CommandResponse> : CommandHandler<C, R> {
        override fun handle(
            command: C,
            userSession: UserSession?,
            request: HttpServletRequest,
            response: HttpServletResponse
        ): R {
            if (userSession == null) {
                throw RuntimeException()
            }
            return handle(command, userSession)
        }

        abstract fun handle(command: C, userSession: UserSession): R
    }
}
