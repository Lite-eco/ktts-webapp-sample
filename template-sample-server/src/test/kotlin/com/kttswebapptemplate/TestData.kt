package com.kttswebapptemplate

import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserStatus
import com.kttswebapptemplate.repository.user.UserDao
import java.time.LocalDateTime
import java.time.ZoneId

object TestData {

    // TODO[tmpl] naming fake, sample
    fun dummyUser(userId: UserId): UserDao.Record {
        val date = LocalDateTime.of(2017, 6, 18, 1, 2).atZone(ZoneId.of("Europe/Paris")).toInstant()
        return UserDao.Record(
            id = userId,
            mail = "mail",
            displayName = "displayName",
            language = Language.En,
            status = UserStatus.Active,
            role = Role.User,
            signupDate = date,
            lastUpdate = date)
    }
}
