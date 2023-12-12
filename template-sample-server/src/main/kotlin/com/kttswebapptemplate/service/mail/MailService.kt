package com.kttswebapptemplate.service.mail

import com.kttswebapptemplate.domain.ApplicationEnvironment
import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.Mail
import com.kttswebapptemplate.domain.MailData
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.repository.log.MailingLogDao
import com.kttswebapptemplate.serialization.Serializer
import com.kttswebapptemplate.service.utils.ApplicationInstance
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.random.RandomService
import kotlin.math.min
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailingLogDao: MailingLogDao,
    private val dateService: DateService,
    private val mailSendingService: MailSendingService,
    private val randomService: RandomService,
) {

    companion object {
        val applicationMailSenderContact =
            Mail.Contact("TemplateSample contact", "contact@example.com")

        fun extractMailPrefixSuffix(mail: String): Pair<String, String> {
            val arobaseLimit = mail.indexOf('@').let { if (it != -1) it else mail.length }
            val plusLimit = mail.indexOf('+').let { if (it != -1) it else mail.length }
            return mail.substring(0, min(arobaseLimit, plusLimit)) to
                mail.substring(min(arobaseLimit + 1, mail.length))
        }
    }

    fun sendMail(
        sender: Mail.Contact,
        recipient: Mail.Contact,
        mailData: MailData,
        userId: UserId,
        language: Language
    ) {
        val (subject, content) =
            MailTemplates.mailSubjectAndData(mailData, language).let { (subject, content) ->
                val finalSubject =
                    when (ApplicationInstance.env) {
                        ApplicationEnvironment.Prod -> subject
                        ApplicationEnvironment.Dev,
                        ApplicationEnvironment.Staging,
                        ApplicationEnvironment.Test -> "[${ApplicationInstance.env}] $subject"
                    }
                finalSubject to content
            }
        val log =
            MailingLogDao.Record(
                id = randomService.id(),
                userId = userId,
                senderName = sender.name,
                senderMail = sender.mail,
                recipientName = recipient.name,
                recipientMail = recipient.mail,
                subject = subject,
                content = content,
                data = Serializer.serialize(mailData),
                logDate = dateService.now())
        mailingLogDao.insert(log)
        mailSendingService.sendMail(Mail(sender, recipient, subject, content), log.id)
    }
}
