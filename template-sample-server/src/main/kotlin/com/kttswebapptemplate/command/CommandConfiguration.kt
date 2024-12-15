package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.Role.Admin
import com.kttswebapptemplate.domain.Role.User

object CommandConfiguration {

    fun role(command: Command): Role? =
        when (command) {
            is AdminUpdateRoleCommand,
            AdminUpdateSessionsCommand,
            is AdminUpdateStatusCommand,
            is AdminUpdateUserMailCommand -> Admin
            is DevLoginCommand -> null
            is LoginCommand -> null
            is RegisterCommand -> null
            is ResetLostPasswordCommand -> null
            is SendLostPasswordMailCommand -> null
            is UpdatePasswordCommand -> User
            is ValidateMailCommand -> null
        }
}
