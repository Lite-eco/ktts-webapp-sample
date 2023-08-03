package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class AdminUpdateRolesCommandHandler(private val userService: UserService) :
    CommandHandler.SessionHandler<AdminUpdateRolesCommand, EmptyCommandResponse>() {

    override fun handle(
        command: AdminUpdateRolesCommand,
        userSession: UserSession
    ): EmptyCommandResponse {
        userService.updateRoles(command.userId, command.roles)
        return EmptyCommandResponse
    }
}
