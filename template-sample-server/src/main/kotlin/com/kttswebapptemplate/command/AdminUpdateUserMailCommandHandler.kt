package com.kttswebapptemplate.command

import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class AdminUpdateUserMailCommandHandler(private val userService: UserService) :
    CommandHandler.Handler<AdminUpdateUserMailCommand, EmptyCommandResponse>() {

    override fun handle(command: AdminUpdateUserMailCommand): EmptyCommandResponse {
        userService.updateMail(command.userId, command.mail)
        return EmptyCommandResponse
    }
}
