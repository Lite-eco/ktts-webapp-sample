package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.UserAccountOperationTokenType
import com.kttswebapptemplate.domain.UserInfos
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.repository.user.UserAccountOperationTokenDao
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.service.user.UserService
import com.kttswebapptemplate.service.user.UserSessionService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class ResetLostPasswordCommandHandler(
    private val userDao: UserDao,
    private val userAccountOperationTokenDao: UserAccountOperationTokenDao,
    private val userService: UserService,
    private val userSessionService: UserSessionService
) : CommandHandler<ResetLostPasswordCommand, ResetLostPasswordCommandResponse> {

    override fun handle(
        command: ResetLostPasswordCommand,
        userSession: UserSession?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResetLostPasswordCommandResponse {
        if (userSession != null) {
            throw RuntimeException("User is already logged in: $userSession")
        }
        val t = userAccountOperationTokenDao.fetchOrNull(command.token)
        if (t == null || t.tokenType != UserAccountOperationTokenType.LostPassword) {
            throw IllegalArgumentException("${command.token}")
        }
        val user = userDao.fetch(t.userId)
        userService.updatePassword(user.id, command.newPassword)
        userSessionService.authenticateUser(user, request, response)
        return ResetLostPasswordCommandResponse(UserInfos.from(user))
    }
}
