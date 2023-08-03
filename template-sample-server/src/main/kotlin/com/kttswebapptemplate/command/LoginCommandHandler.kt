package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.LoginResult
import com.kttswebapptemplate.domain.UserInfos
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.service.user.UserService
import com.kttswebapptemplate.service.user.UserSessionService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class LoginCommandHandler(
    private val userDao: UserDao,
    private val userService: UserService,
    private val userSessionService: UserSessionService
) : CommandHandler<LoginCommand, LoginCommandResponse> {

    override fun handle(
        command: LoginCommand,
        userSession: UserSession?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): LoginCommandResponse {
        if (userSession != null) {
            // FIXME[tmpl] can't find the wooord
            // TemplateSampleCommonException
            // TemplateSampleStandardException
            throw RuntimeException()
        }
        val cleanMail = UserService.cleanMail(command.mail)
        val user =
            userDao.fetchOrNullByMail(cleanMail)
                ?: return LoginCommandResponse(LoginResult.MailNotFound, null)
        val userPassword = userDao.fetchPassword(user.id)
        if (!userService.passwordMatches(command.password, userPassword)) {
            return LoginCommandResponse(LoginResult.BadPassword, null)
        }
        userSessionService.authenticateUser(user, request, response)
        return LoginCommandResponse(LoginResult.LoggedIn, UserInfos.fromUser(user))
    }
}
