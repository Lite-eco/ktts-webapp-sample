package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.domain.HashedPassword
import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.Mail
import com.kttswebapptemplate.domain.MailData
import com.kttswebapptemplate.domain.PlainStringPassword
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.Uri
import com.kttswebapptemplate.domain.UserAccountOperationToken
import com.kttswebapptemplate.domain.UserAccountOperationTokenType
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.domain.UserStatus
import com.kttswebapptemplate.repository.user.UserAccountOperationTokenDao
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.repository.user.UserMailLogDao
import com.kttswebapptemplate.repository.user.UserSessionLogDao
import com.kttswebapptemplate.service.mail.MailService
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.NotificationService
import com.kttswebapptemplate.service.utils.random.RandomService
import com.kttswebapptemplate.utils.TemplateSampleStringUtils
import java.time.Duration
import java.time.temporal.ChronoUnit
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Value("\${app.url}") private val appUrl: Uri,
    private val userDao: UserDao,
    private val userMailLogDao: UserMailLogDao,
    private val userSessionLogDao: UserSessionLogDao,
    private val accountTokenDao: UserAccountOperationTokenDao,
    private val dateService: DateService,
    private val randomService: RandomService,
    private val notificationService: NotificationService,
    private val passwordEncoder: PasswordEncoder,
    private val mailService: MailService,
    private val userSessionService: UserSessionService
) {

    private val logger = KotlinLogging.logger {}

    companion object {
        val mailValidationTokenValidityDuration = Duration.of(2, ChronoUnit.DAYS)

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
        password: PlainStringPassword,
        displayName: String,
        language: Language,
    ): UserDao.Record {
        val now = dateService.now()
        val (cleanMail, dirtyMail) = cleanMailAndReturnDirty(mail)
        val user =
            UserDao.Record(
                    id = randomService.id(),
                    mail = cleanMail,
                    displayName = displayName.trim(),
                    language = language,
                    role = Role.User,
                    status = UserStatus.MailValidationPending,
                    signupDate = now,
                    lastUpdateDate = now)
                .also { userDao.insert(it, password.hashPassword(passwordEncoder)) }
        val mailLog =
            UserMailLogDao.Record(
                    randomService.id(), user.id, cleanMail, dirtyMail, false, now, null)
                .also(userMailLogDao::insert)
        notificationService.notify(
            "${user.mail} just subscribed.", NotificationService.Channel.NewUser)
        val token =
            UserAccountOperationTokenDao.Record(
                    randomService.securityString(UserAccountOperationToken.length),
                    UserAccountOperationTokenType.ValidateMail,
                    user.id,
                    mailLog.id,
                    now)
                .also(accountTokenDao::insert)
        val validateMailUrl =
            appUrl.append("?mailValidation=${token.token.rawString}-${user.id.stringUuid()}")
        mailService.sendMail(
            recipient = Mail.Contact(user.displayName, user.mail),
            mailData = MailData.AccountMailValidation(user.displayName, validateMailUrl),
            userId = user.id,
            language = user.language)
        return user
    }

    fun validateMail(token: UserAccountOperationToken) {
        val t = accountTokenDao.fetchOrNull(token) ?: throw IllegalArgumentException("$token")
        if (t.tokenType != UserAccountOperationTokenType.ValidateMail) {
            throw IllegalArgumentException("$token")
        }
        if (!validateToken(t, mailValidationTokenValidityDuration)) {
            // TODO information to the user
            throw IllegalArgumentException("$token")
        }
        synchronized(t.userId) {
            val lastUserMailLog = userMailLogDao.fetchLastByUserId(t.userId)
            // only the last mail should be validated
            if (lastUserMailLog.id != requireNotNull(t.userMailLogId) { "${t.token}" }) {
                throw IllegalArgumentException("${t.token} ${t.userId}")
            }
            if (lastUserMailLog.validated) {
                return
            }
            val userMail = userDao.fetch(t.userId)
            if (userMail.mail != lastUserMailLog.mail) {
                // the user is changing his email
                TODO() // update user
            } else {
                // the user is validating his "first" email
                userMailLogDao.updateValidated(lastUserMailLog.id, true, dateService.now())
                updateStatusOrRoleIfNotNull(
                    userId = t.userId, status = UserStatus.Active, role = null)
            }
        }
    }

    // TODO directly validated is simpler for the moment
    fun updateMail(userId: UserId, mail: String) {
        val (newMail, newDirtyMail) = cleanMailAndReturnDirty(mail)
        val formerMail = userDao.fetchMail(userId)
        val now = dateService.now()
        // [doc] Is done first on purpose. If user has tried to use é@gmail.com, it has been change
        // to e@gmail.com, if he retries we should re-log
        // TODO[tmpl] need integration tests !
        if (newMail == formerMail) {
            // TODO[tmpl] user should be warned (maybe he tried a cleaned email)
            // (can be an accidental double click too)
            return
        }
        userMailLogDao.insert(
            UserMailLogDao.Record(
                randomService.id(), userId, newMail, newDirtyMail, true, now, null))
        logger.info { "Update mail $userId $formerMail => $newMail" }
        userDao.updateMail(userId, newMail, now)
    }

    fun updatePassword(userId: UserId, password: PlainStringPassword) {
        userDao.updatePassword(userId, password.hashPassword(passwordEncoder), dateService.now())
    }

    /**
     * Update UserStatus if set, and Role if set. One fonction for both (instead of 2 functions)
     * because of synchronization.
     */
    fun updateStatusOrRoleIfNotNull(userId: UserId, status: UserStatus?, role: Role?) =
        synchronized(userId) {
            val user = userDao.fetch(userId)
            if (status != null) {
                userDao.updateStatus(userId, status, dateService.now())
            }
            if (role != null) {
                userDao.updateRole(userId, role, dateService.now())
            }
            userSessionLogDao.fetchIdsByUserId(userId).forEach { sessionId ->
                val updatedSession =
                    UserSession(sessionId, userId, status ?: user.status, role ?: user.role)
                userSessionService.updateAndPersistAllExistingSessionsForUser(updatedSession)
            }
        }

    fun passwordMatches(verifyPassword: PlainStringPassword, actualPassword: HashedPassword) =
        verifyPassword.passwordMatches(actualPassword, passwordEncoder)

    private fun validateToken(
        token: UserAccountOperationTokenDao.Record,
        validityDuration: Duration
    ): Boolean {
        val validityDate = token.creationDate.plus(validityDuration)
        return validityDate > dateService.now()
    }
}
