package com.kttswebapptemplate.command

import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class AdminUpdateRoleCommandHandler(private val userService: UserService) :
    CommandHandler.Handler<AdminUpdateRoleCommand, EmptyCommandResponse>() {

    override fun handle(command: AdminUpdateRoleCommand): EmptyCommandResponse {
        userService.updateStatusOrRole(userId = command.userId, status = null, role = command.role)
        return EmptyCommandResponse
    }
}
