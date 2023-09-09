package com.kttswebapptemplate.command

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.kttswebapptemplate.domain.LoginResult
import com.kttswebapptemplate.domain.PlainStringPassword
import com.kttswebapptemplate.domain.RegisterResult
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.UserAccountOperationToken
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserInfos
import com.kttswebapptemplate.domain.UserStatus
import kt2ts.annotation.GenerateTypescript

@GenerateTypescript
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
sealed class Command

@GenerateTypescript
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
sealed class CommandResponse

data object EmptyCommandResponse : CommandResponse()

data class AdminUpdateRoleCommand(val userId: UserId, val role: Role) : Command()

data object AdminUpdateSessions : Command()

data class AdminUpdateStatusCommand(val userId: UserId, val status: UserStatus) : Command()

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

data class UpdatePasswordCommand(val password: PlainStringPassword) : Command()

data class ValidateMailCommand(val token: UserAccountOperationToken) : Command()
