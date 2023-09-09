package com.kttswebapptemplate.service.mail

import com.fasterxml.jackson.databind.ObjectMapper
import com.kttswebapptemplate.domain.Mail
import com.kttswebapptemplate.domain.MailLogId
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class FakeSaasMailService {

    private val logger = KotlinLogging.logger {}

    private val objectMapper by lazy { ObjectMapper() }

    fun sendMail(mail: Mail, mailLogId: MailLogId) {
        logger.info { "Send mail $mailLogId: \n${mail.content}" }
    }
}
