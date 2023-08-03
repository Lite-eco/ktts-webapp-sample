package templatesample.service

import templatesample.domain.Language
import templatesample.domain.PlainStringPassword
import templatesample.domain.Role
import templatesample.repository.user.UserDao
import templatesample.service.user.UserService
import templatesample.service.utils.TransactionIsolationService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
// TODO[tmpl] naming fake / sample
class DevInitialDataInjectorService(
    @Value("\${mail.devDestination}") private val developerDestinationMail: String,
    private val userDao: UserDao,
    private val dateService: DateService,
    private val randomService: RandomService,
    private val userService: UserService,
    private val transactionIsolationService: TransactionIsolationService
) {

    fun initiateDevData() {
        transactionIsolationService.execute {
            insertUser("user", false)
            insertUser("admin", true)
        }
    }

    private fun insertUser(
        username: String,
        admin: Boolean,
    ) {
        val mail = devUserMail(username)
        if (userDao.fetchOrNullByMail(mail) == null) {
            val now = dateService.now()
            userDao.insert(
                UserDao.Record(
                    id = randomService.id(),
                    mail = mail,
                    username = username,
                    displayName = username,
                    language = Language.En,
                    roles = setOf(Role.User).let { if (admin) it + Role.Admin else it },
                    signupDate = now,
                    lastUpdate = now),
                userService.hashPassword(PlainStringPassword(username)))
        }
    }

    fun devUserMail(username: String): String {
        // FIXME[tmpl] double + if some + in conf (which is the case...) !
        val arobaseIndex = developerDestinationMail.indexOf('@')
        val mailPrefix = developerDestinationMail.substring(0, arobaseIndex)
        val mailSuffix = developerDestinationMail.substring(arobaseIndex)
        return "$mailPrefix+$username$mailSuffix"
    }
}
