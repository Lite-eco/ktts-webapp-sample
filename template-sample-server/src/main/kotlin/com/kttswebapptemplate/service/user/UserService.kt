package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.config.SafeSessionRepository
import com.kttswebapptemplate.domain.AuthLogType
import com.kttswebapptemplate.domain.HashedPassword
import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.PlainStringPassword
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.repository.user.UserMailLogDao
import com.kttswebapptemplate.repository.user.UserSessionLogDao
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.NotificationService
import com.kttswebapptemplate.service.utils.TransactionIsolationService
import com.kttswebapptemplate.service.utils.random.RandomService
import com.kttswebapptemplate.utils.TemplateSampleStringUtils
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.session.Session as SpringSession
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDao: UserDao,
    private val userMailLogDao: UserMailLogDao,
    private val userSessionLogDao: UserSessionLogDao,
    private val dateService: DateService,
    private val randomService: RandomService,
    private val notificationService: NotificationService,
    private val transactionIsolationService: TransactionIsolationService,
    private val sessionRepository: SafeSessionRepository,
    private val passwordEncoder: PasswordEncoder
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

    fun updatePassword(userId: UserId, password: PlainStringPassword) {
        userDao.updatePassword(userId, hashPassword(password), dateService.now())
    }

    fun updateRoles(userId: UserId, roles: Set<Role>) {
        userDao.updateRoles(userId, roles, dateService.now())
        userSessionLogDao.fetchIdsByUserId(userId).forEach { sessionId ->
            val userSession = UserSession(sessionId, userId, roles)
            val userSessionPrincipalName = userSession.toString()
            sessionRepository.findByPrincipalName(userSessionPrincipalName).values.forEach {
                updateSession(it, userSession)
            }
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
}
