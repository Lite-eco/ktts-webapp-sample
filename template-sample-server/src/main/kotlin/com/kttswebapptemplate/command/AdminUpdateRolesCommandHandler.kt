package com.kttswebapptemplate.command

import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class AdminUpdateRolesCommandHandler(private val userService: UserService) :
    CommandHandler.Handler<AdminUpdateRolesCommand, EmptyCommandResponse>() {

    override fun handle(command: AdminUpdateRolesCommand): EmptyCommandResponse {
        userService.updateRoles(command.userId, command.roles)
        return EmptyCommandResponse
    }
}
