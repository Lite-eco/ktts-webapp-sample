package com.kttswebapptemplate.domain

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.kttswebapptemplate.repository.user.UserDao

enum class Language {
    En,
    Test
}

enum class UserStatus {
    MailValidationPending,
    Active,
    Disabled,
}

enum class Role {
    User,
    Admin
}

data class UserInfos(
    val id: UserId,
    val mail: String,
    val displayName: String,
    //    val zoneId: ZoneId,
    val status: UserStatus,
    val role: Role
) {
    companion object {
        fun fromUser(user: UserDao.Record) =
            UserInfos(
                user.id,
                user.mail,
                user.displayName,
                //            user.zoneId,
                user.status,
                user.role)
    }
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
sealed class Session

// TODO[tmpl] check this
/**
 * To update UserSession:
 * * rename former class to @JsonTypeName("UserSession-vX") data class FormerUserSession(..)
 * * new class @JsonTypeName("UserSession-vX+1") data class UserSession(..)
 * * Convert former into new session:
 * * update UserSessionHelper.authenticateUser
 * * update UserSessionHelper.getUserSession (should fail)
 */
@JsonTypeName("UserSession-v0")
data class UserSession(
    val sessionId: UserSessionId,
    val userId: UserId,
    val status: UserStatus,
    val role: Role
) : Session() {
    // [doc] for logback and spring sessions
    override fun toString() = "[$sessionId|$userId]"
}

enum class LoginResult {
    LoggedIn,
    MailNotFound,
    BadPassword
}

enum class RegisterResult {
    Registered,
    MailAlreadyExists
}

enum class UpdatePasswordResult {
    BadPassword,
    PasswordChanged
}
