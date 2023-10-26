package com.kttswebapptemplate.command

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.kttswebapptemplate.domain.LoginResult
import com.kttswebapptemplate.domain.PlainStringPassword
import com.kttswebapptemplate.domain.RegisterResult
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.UpdatePasswordResult
import com.kttswebapptemplate.domain.UserAccountOperationToken
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserInfos
import com.kttswebapptemplate.domain.UserStatus
import kt2ts.annotation.GenerateTypescript

@GenerateTypescript
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
sealed class Command

// [doc] is sealed for generation selection
@GenerateTypescript sealed class CommandResponse

data object EmptyCommandResponse : CommandResponse()

data class AdminUpdateRoleCommand(val userId: UserId, val role: Role) : Command()

data object AdminUpdateSessionsCommand : Command()

data class AdminUpdateStatusCommand(val userId: UserId, val status: UserStatus) : Command()

data class AdminUpdateUserMailCommand(val userId: UserId, val mail: String) : Command()

data class DevLoginCommand(val username: String) : Command()

data class DevLoginCommandResponse(val userInfos: UserInfos) : CommandResponse()

data class LoginCommand(val mail: String, val password: PlainStringPassword) : Command()

data class LoginCommandResponse(val result: LoginResult, val userInfos: UserInfos?) :
    CommandResponse()

data class RegisterCommand(
    val mail: String,
    val password: PlainStringPassword,
    val displayName: String
) : Command()

data class RegisterCommandResponse(val result: RegisterResult, val userInfos: UserInfos?) :
    CommandResponse()

data class UpdateLostPasswordCommand(
    val token: UserAccountOperationToken,
    val newPassword: PlainStringPassword
) : Command()

data class UpdatePasswordCommand(
    val currentPassword: PlainStringPassword,
    val newPassword: PlainStringPassword
) : Command()

data class UpdatePasswordCommandResponse(val result: UpdatePasswordResult) : CommandResponse()

data class ValidateMailCommand(val token: UserAccountOperationToken) : Command()
