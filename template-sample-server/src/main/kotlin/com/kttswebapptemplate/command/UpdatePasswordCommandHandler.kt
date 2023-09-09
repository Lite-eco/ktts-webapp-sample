package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.UpdatePasswordResult
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class UpdatePasswordCommandHandler(
    private val userDao: UserDao,
    private val userService: UserService
) : CommandHandler.SessionHandler<UpdatePasswordCommand, UpdatePasswordCommandResponse>() {

    override fun handle(
        command: UpdatePasswordCommand,
        userSession: UserSession
    ): UpdatePasswordCommandResponse {
        val userPassword = userDao.fetchPassword(userSession.userId)
        if (!userService.passwordMatches(command.currentPassword, userPassword)) {
            return UpdatePasswordCommandResponse(UpdatePasswordResult.BadPassword)
        }
        userService.updatePassword(userSession.userId, command.newPassword)
        return UpdatePasswordCommandResponse(UpdatePasswordResult.PasswordChanged)
    }
}
