package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.Role.Admin
import com.kttswebapptemplate.domain.Role.User

object CommandConfiguration {

    fun role(command: Command): Role? =
        when (command) {
            is AdminUpdateRoleCommand -> Admin
            AdminUpdateSessions -> Admin
            is AdminUpdateStatusCommand -> Admin
            is DevLoginCommand -> null
            is LoginCommand -> null
            is RegisterCommand -> null
            is UpdateLostPasswordCommand -> TODO()
            is UpdatePasswordCommand -> User
            is ValidateMailCommand -> null
        }
}
