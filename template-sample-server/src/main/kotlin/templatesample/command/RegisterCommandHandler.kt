package templatesample.command

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import templatesample.domain.RegisterResult
import templatesample.domain.UserInfos
import templatesample.domain.UserSession
import templatesample.error.MailAlreadyRegisteredException
import templatesample.service.user.LocaleService
import templatesample.service.user.UserService
import templatesample.service.user.UserSessionService

@Service
class RegisterCommandHandler(
    private val userService: UserService,
    private val userSessionService: UserSessionService,
    private val localeService: LocaleService
) : CommandHandler<RegisterCommand, RegisterCommandResponse> {

    companion object {
        // TODO[tmpl] those validations should be done in another place too. Also :
        // * should not be longer than 255 chars (because of the database)
        fun validateRegisterCommand(c: RegisterCommand) {
            // TODO[tmpl] use require
            if (c.mail.isBlank()) throw IllegalArgumentException("Mail is blank")
            if (c.password.password.isBlank()) throw IllegalArgumentException("Password is blank")
            if (c.displayName.isBlank()) throw IllegalArgumentException("Display name is blank")
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
            throw RuntimeException("$userSession")
        }
        validateRegisterCommand(command)
        val user =
            try {
                userService.createUser(
                    command.mail.trim(),
                    userService.hashPassword(command.password),
                    command.displayName,
                    localeService.selectLanguage(request.locales))
            } catch (e: MailAlreadyRegisteredException) {
                return RegisterCommandResponse(RegisterResult.MailAlreadyExists, null)
            }
        userSessionService.authenticateUser(user, request, response)
        return RegisterCommandResponse(RegisterResult.Registered, UserInfos.fromUser(user))
    }
}
