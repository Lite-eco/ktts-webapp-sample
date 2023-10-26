package com.kttswebapptemplate.service.mail

import com.kttswebapptemplate.domain.Mail
import com.kttswebapptemplate.domain.MailLogId
import mu.KotlinLogging

class DevFakeMailSendingService : MailSendingService {

    private val logger = KotlinLogging.logger {}

    override fun sendMail(mail: Mail, mailLogId: MailLogId) {
        logger.info { "Send mail $mailLogId: \n${mail.content}" }
    }
}
