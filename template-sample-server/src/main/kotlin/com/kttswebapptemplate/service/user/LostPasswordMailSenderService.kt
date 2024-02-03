package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.config.Routes
import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.Mail
import com.kttswebapptemplate.domain.MailData
import com.kttswebapptemplate.domain.Uri
import com.kttswebapptemplate.domain.UserAccountOperationToken
import com.kttswebapptemplate.domain.UserAccountOperationTokenType
import com.kttswebapptemplate.repository.user.UserAccountOperationTokenDao
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.service.mail.MailService
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.random.RandomService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LostPasswordMailSenderService(
    @Value("\${app.url}") private val appUrl: Uri,
    private val userAccountOperationTokenDao: UserAccountOperationTokenDao,
    private val mailService: MailService,
    private val dateService: DateService,
    private val randomService: RandomService,
) {

    private val logger = KotlinLogging.logger {}

    fun sendLostPasswordMail(user: UserDao.Record) {
        logger.info { "Send lost password mail to $user" }
        val token =
            UserAccountOperationTokenDao.Record(
                token = randomService.securityString(UserAccountOperationToken.length),
                tokenType = UserAccountOperationTokenType.LostPassword,
                userId = user.id,
                userMailLogId = null,
                creationDate = dateService.now())
        userAccountOperationTokenDao.insert(token)
        val url = appUrl.resolve(Routes.resetPassword).append("?authToken=${token.token.rawString}")
        mailService.sendMail(
            recipient = Mail.Contact(user.displayName, user.mail),
            mailData = MailData.LostPassword(user.displayName, url),
            userId = user.id,
            language = user.language)
    }

    fun sendLostPasswordNoAccountMail(mail: String, language: Language) {
        logger.info { "Send lost password but no account mail to $mail" }
        val url = appUrl.resolve(Routes.register)
        mailService.sendMail(
            recipient = Mail.Contact(mail, mail),
            mailData = MailData.LostPasswordNoAccount(mail, url),
            userId = null,
            language = language)
    }
}
