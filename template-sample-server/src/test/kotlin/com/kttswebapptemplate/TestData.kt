package com.kttswebapptemplate

import com.kttswebapptemplate.config.ApplicationConstants
import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.repository.user.UserDao
import java.time.LocalDateTime

object TestData {

    // TODO[tmpl] naming fake, sample
    fun dummyUser(userId: UserId): UserDao.Record {
        val date =
            LocalDateTime.of(2017, 6, 18, 1, 2).atZone(ApplicationConstants.parisZoneId).toInstant()
        return UserDao.Record(
            id = userId,
            mail = "mail",
            displayName = "displayName",
            language = Language.En,
            roles = setOf(Role.User),
            signupDate = date,
            lastUpdate = date)
    }
}
