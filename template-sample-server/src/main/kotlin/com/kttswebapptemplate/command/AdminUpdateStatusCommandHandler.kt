package com.kttswebapptemplate.command

import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class AdminUpdateStatusCommandHandler(private val userService: UserService) :
    CommandHandler.Handler<AdminUpdateStatusCommand, EmptyCommandResponse>() {

    override fun handle(command: AdminUpdateStatusCommand): EmptyCommandResponse {
        userService.updateStatusOrRole(
            userId = command.userId, status = command.status, role = null)
        return EmptyCommandResponse
    }
}
