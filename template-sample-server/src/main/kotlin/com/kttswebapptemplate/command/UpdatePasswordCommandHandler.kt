package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class UpdatePasswordCommandHandler(private val userService: UserService) :
    CommandHandler.SessionHandler<UpdatePasswordCommand, EmptyCommandResponse>() {

    override fun handle(
        command: UpdatePasswordCommand,
        userSession: UserSession
    ): EmptyCommandResponse {
        userService.updatePassword(userSession.userId, command.password)
        return EmptyCommandResponse
    }
}
