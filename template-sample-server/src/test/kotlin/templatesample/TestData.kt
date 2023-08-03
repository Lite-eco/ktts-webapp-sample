package templatesample

import templatesample.config.ApplicationConstants
import templatesample.domain.Language
import templatesample.domain.Role
import templatesample.domain.UserId
import templatesample.repository.user.UserDao
import java.time.LocalDateTime

object TestData {

    // TODO[tmpl] naming fake, sample
    fun dummyUser(userId: UserId): UserDao.Record {
        val date =
            LocalDateTime.of(2017, 6, 18, 1, 2).atZone(ApplicationConstants.parisZoneId).toInstant()
        return UserDao.Record(
            id = userId,
            mail = "mail",
            username = "username",
            displayName = "displayName",
            language = Language.en,
            roles = setOf(Role.user),
            dirtyMail = "dirtyMail",
            signupDate = date,
            lastUpdateDate = date)
    }
}
