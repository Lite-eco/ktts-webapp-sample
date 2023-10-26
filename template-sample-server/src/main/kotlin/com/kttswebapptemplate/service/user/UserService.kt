package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.config.ApplicationConstants
import com.kttswebapptemplate.config.SafeSessionRepository
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
import com.kttswebapptemplate.service.utils.TransactionIsolationService
import com.kttswebapptemplate.service.utils.random.RandomService
import com.kttswebapptemplate.utils.TemplateSampleStringUtils
import java.time.Duration
import java.time.temporal.ChronoUnit
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.session.Session as SpringSession
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
    private val transactionIsolationService: TransactionIsolationService,
    private val sessionRepository: SafeSessionRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mailService: MailService
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
        hashedPassword: HashedPassword,
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
                lastUpdate = now)
        userDao.insert(user, hashedPassword)
        val mailLog =
            UserMailLogDao.Record(
                randomService.id(), user.id, cleanMail, dirtyMail, false, now, null)
        userMailLogDao.insert(mailLog)
        notificationService.notify(
            "${user.mail} just subscribed.", NotificationService.Channel.NewUser)
        val token =
            UserAccountOperationTokenDao.Record(
                randomService.securityString(UserAccountOperationToken.length),
                UserAccountOperationTokenType.ValidateMail,
                user.id,
                mailLog.id,
                now)
        accountTokenDao.insert(token)
        val validateMailUrl =
            appUrl.append("?mailValidation=${token.token.rawString}-${user.id.stringUuid()}")
        mailService.sendMail(
            ApplicationConstants.applicationMailSenderContact,
            Mail.Contact(user.displayName, user.mail),
            MailData.AccountMailValidation(user.displayName, validateMailUrl),
            user.id,
            user.language)
        return user
    }

    fun validateMail(token: UserAccountOperationToken) {
        val t =
            accountTokenDao.fetchOrNull(token, UserAccountOperationTokenType.ValidateMail)
                ?: throw IllegalArgumentException("$token")
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
        userDao.updatePassword(userId, hashPassword(password), dateService.now())
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
                sessionRepository
                    .findByPrincipalName(
                        // toString() here is ok cause Spring does it
                        updatedSession.toString())
                    .values
                    .forEach { updateSession(it, updatedSession) }
            }
        }

    fun updateSession(session: SpringSession, userSession: UserSession) {
        logger.info { "Save up-to-date session ${session.id}" }
        val springAuthentication = UsernamePasswordAuthenticationToken(userSession, null, null)
        val context =
            session.getAttribute<SecurityContextImpl>(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)
        context.authentication = springAuthentication
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context)
        transactionIsolationService.execute { sessionRepository.save(session) }
    }

    fun hashPassword(password: PlainStringPassword): HashedPassword {
        require(password.password.isNotBlank()) { "Password is blank" }
        return HashedPassword(passwordEncoder.encode(password.password.trim()))
    }

    fun passwordMatches(verifyPassword: PlainStringPassword, actualPassword: HashedPassword) =
        passwordEncoder.matches(verifyPassword.password.trim(), actualPassword.hash)

    private fun validateToken(
        token: UserAccountOperationTokenDao.Record,
        validityDuration: Duration
    ): Boolean {
        val validityDate = token.creationDate.plus(validityDuration)
        return validityDate > dateService.now()
    }
}
