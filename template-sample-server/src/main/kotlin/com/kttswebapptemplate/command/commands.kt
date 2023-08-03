package com.kttswebapptemplate.command

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.kttswebapptemplate.domain.LoginResult
import com.kttswebapptemplate.domain.PlainStringPassword
import com.kttswebapptemplate.domain.RegisterResult
import com.kttswebapptemplate.domain.UserInfos
import kttots.Shared

@Shared
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
sealed class Command

@Shared sealed class CommandResponse

object EmptyCommandResponse : CommandResponse()

data class DevLoginCommand(val username: String) : Command()

data class DevLoginCommandResponse(val userinfos: UserInfos) : CommandResponse()

data class LoginCommand(val mail: String, val password: PlainStringPassword) : Command()

data class LoginCommandResponse(val result: LoginResult, val userinfos: UserInfos?) :
    CommandResponse()

data class RegisterCommand(
    val mail: String,
    val password: PlainStringPassword,
    val displayName: String
) : Command()

data class RegisterCommandResponse(val result: RegisterResult, val userinfos: UserInfos?) :
    CommandResponse()