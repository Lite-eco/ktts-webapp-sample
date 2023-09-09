package com.kttswebapptemplate.command

import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class ValidateMailCommandHandler(private val userService: UserService) :
    CommandHandler.Handler<ValidateMailCommand, EmptyCommandResponse>() {

    override fun handle(command: ValidateMailCommand): EmptyCommandResponse {
        userService.validateMail(command.token)
        return EmptyCommandResponse
    }
}
