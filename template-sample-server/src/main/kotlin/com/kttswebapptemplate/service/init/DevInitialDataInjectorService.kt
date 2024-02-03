package com.kttswebapptemplate.service.init

import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.PlainStringPassword
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.UserStatus
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.service.mail.MailService
import com.kttswebapptemplate.service.user.UserService
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.TransactionIsolationService
import com.kttswebapptemplate.service.utils.random.RandomService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
// TODO[tmpl] naming fake / sample
class DevInitialDataInjectorService(
    @Value("\${mailing.devDestination}") private val developerDestinationMail: String,
    private val userDao: UserDao,
    private val dateService: DateService,
    private val randomService: RandomService,
    private val userService: UserService,
    private val transactionIsolationService: TransactionIsolationService
) {

    fun initiateDevData() {
        transactionIsolationService.execute {
            insertUser("user", Role.User)
            insertUser("admin", Role.Admin)
        }
    }

    private fun insertUser(
        username: String,
        role: Role,
    ) {
        val mail = devUserMail(username)
        if (userDao.fetchOrNullByMail(mail) == null) {
            val now = dateService.now()
            userDao.insert(
                UserDao.Record(
                    id = randomService.id(),
                    mail = mail,
                    displayName = username,
                    language = Language.En,
                    role = role,
                    status = UserStatus.Active,
                    signupDate = now,
                    lastUpdateDate = now),
                userService.hashPassword(PlainStringPassword(username)))
        }
    }

    fun devUserMail(username: String): String {
        val (mailPrefix, mailSuffix) = MailService.extractMailPrefixSuffix(developerDestinationMail)
        // not a problem if there's multiple '+'
        return "$mailPrefix+$username@$mailSuffix"
    }
}
