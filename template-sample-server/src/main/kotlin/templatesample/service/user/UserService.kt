package templatesample.service.user

import templatesample.domain.AuthLogType
import templatesample.domain.HashedPassword
import templatesample.domain.Language
import templatesample.domain.PlainStringPassword
import templatesample.domain.Role
import templatesample.domain.UserId
import templatesample.repository.user.UserDao
import templatesample.repository.user.UserMailLogDao
import templatesample.service.DateService
import templatesample.service.NotificationService
import templatesample.service.RandomService
import templatesample.utils.TemplateSampleStringUtils
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    val userDao: UserDao,
    val userMailLogDao: UserMailLogDao,
    val dateService: DateService,
    val randomService: RandomService,
    val notificationService: NotificationService,
    val passwordEncoder: PasswordEncoder
) {

    private val logger = KotlinLogging.logger {}

    companion object {
        fun cleanMail(dirtyMail: String) =
            dirtyMail
                .lowercase()
                .replace("\t", "")
                .replace(" ", "")
                // [doc] accents are supposed to be supported by the RFC
                // but in practice it's always a user input error
                .let { TemplateSampleStringUtils.removeAccents(it) }

        fun cleanMailAndReturnDirty(dirtyMail: String) = let {
            val clean = cleanMail(dirtyMail)
            // [doc] is supposed to be trimed before
            val dirty = dirtyMail.trim()
            clean to (if (dirty != clean) dirty else null)
        }
    }

    fun createUser(
        mail: String,
        hashedPassword: HashedPassword,
        displayName: String,
        language: Language,
    ): UserDao.Record {
        val (cleanMail, dirtyMail) = cleanMailAndReturnDirty(mail)
        val now = dateService.now()
        val user =
            UserDao.Record(
                id = randomService.id(),
                mail = cleanMail,
                username = null,
                displayName = displayName.trim(),
                language = language,
                roles = setOf(Role.User),
                signupDate = now,
                lastUpdate = now)
        userDao.insert(user, hashedPassword)
        if (dirtyMail != null) {
            userMailLogDao.insert(
                UserMailLogDao.Record(
                    randomService.id(), user.id, dirtyMail, AuthLogType.DirtyMail, now))
        }
        notificationService.notify(
            "${user.mail} just suscribed.", NotificationService.Channel.NewUser)
        return user
    }

    fun updateMail(userId: UserId, mail: String) {
        val (newMail, newDirtyMail) = cleanMailAndReturnDirty(mail)
        val formerMail = userDao.fetchMail(userId)
        val now = dateService.now()
        // [doc] Is done first on purpose. If user has tried to use é@gmail.com, it has been change
        // to e@gmail.com, if he retries we should re-log
        // TODO[tmpl] need integration tests !
        if (newDirtyMail != null) {
            userMailLogDao.insert(
                UserMailLogDao.Record(
                    randomService.id(), userId, newDirtyMail, AuthLogType.DirtyMail, now))
        }
        if (newMail == formerMail) {
            // TODO[tmpl] user should be warned (maybe he tried a cleaned email)
            // (can be an accidental double click too)
            return
        }
        logger.info { "Update mail $userId $formerMail => $newMail" }
        userDao.updateMail(userId, newMail, now)
        userMailLogDao.insert(
            UserMailLogDao.Record(
                randomService.id(), userId, formerMail, AuthLogType.FormerMail, now))
    }

    fun hashPassword(password: PlainStringPassword): HashedPassword {
        require(password.password.isNotBlank()) { "Password is blank" }
        return HashedPassword(passwordEncoder.encode(password.password.trim()))
    }

    fun passwordMatches(verifyPassword: PlainStringPassword, actualPassword: HashedPassword) =
        passwordEncoder.matches(verifyPassword.password.trim(), actualPassword.hash)
}
