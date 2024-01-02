package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.config.Routes
import com.kttswebapptemplate.domain.Mail
import com.kttswebapptemplate.domain.MailData
import com.kttswebapptemplate.domain.Uri
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.service.mail.MailService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LostPasswordMailSenderService(
    @Value("\${app.url}") private val appUrl: Uri,
    private val mailService: MailService,
) {

    private val logger = KotlinLogging.logger {}

    data class LostPasswordMailPayload(val url: Uri, val invalidateTokenUrl: Uri)

    fun sendMail(user: UserDao.Record) {
        val url = appUrl.resolve(Routes.loginUpdatePassword)
        logger.info { "Send lost password mail to $user" }
        mailService.sendMail(
            sender = MailService.applicationMailSenderContact,
            recipient = Mail.Contact(user.displayName, user.mail),
            mailData = MailData.LostPassword(url),
            userId = user.id,
            language = user.language)
    }
}
