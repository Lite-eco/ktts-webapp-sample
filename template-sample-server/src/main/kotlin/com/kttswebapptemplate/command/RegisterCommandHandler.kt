package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.RegisterResult
import com.kttswebapptemplate.domain.UserInfos
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.error.MailAlreadyRegisteredException
import com.kttswebapptemplate.service.user.LocaleService
import com.kttswebapptemplate.service.user.UserService
import com.kttswebapptemplate.service.user.UserSessionService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class RegisterCommandHandler(
    private val userService: UserService,
    private val userSessionService: UserSessionService,
    private val localeService: LocaleService
) : CommandHandler<RegisterCommand, RegisterCommandResponse> {

    val logger = KotlinLogging.logger {}

    companion object {
        // TODO[tmpl] those validations should be done in another place too. Also :
        // * should not be longer than 255 chars (because of the database)
        fun validateRegisterCommand(c: RegisterCommand) {
            // TODO[tmpl] use require
            require(c.mail.isNotBlank()) { "Mail is blank" }
            require(c.password.isNotBlank()) { "Password is blank" }
            require(c.displayName.isNotBlank()) { "Display name is blank" }
        }
    }

    override fun handle(
        command: RegisterCommand,
        userSession: UserSession?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): RegisterCommandResponse {
        if (userSession != null) {
            // FIXME[tmpl] can't find the wooord
            // TemplateSampleCommonException
            // TemplateSampleStandardException
            throw RuntimeException("User is already logged in: $userSession")
        }
        validateRegisterCommand(command)
        val user =
            try {
                userService.createUser(
                    command.mail.trim(),
                    command.password,
                    command.displayName,
                    localeService.selectLanguage(request.locales.toList()))
            } catch (e: MailAlreadyRegisteredException) {
                logger.info {
                    "Someone tried to register with already existing mail ${command.mail.trim()}"
                }
                return RegisterCommandResponse(RegisterResult.MailAlreadyExists, null)
            }
        userSessionService.authenticateUser(user, request, response)
        return RegisterCommandResponse(RegisterResult.Registered, UserInfos.from(user))
    }
}
