package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.Role.Admin

object CommandConfiguration {

    fun role(command: Command): Role? =
        when (command) {
            is AdminUpdateRolesCommand -> Admin
            is DevLoginCommand -> null
            is LoginCommand -> null
            is RegisterCommand -> null
        }
}
