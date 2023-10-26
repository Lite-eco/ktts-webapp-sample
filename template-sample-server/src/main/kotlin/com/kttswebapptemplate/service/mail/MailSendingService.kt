package com.kttswebapptemplate.service.mail

import com.kttswebapptemplate.domain.Mail
import com.kttswebapptemplate.domain.MailLogId

interface MailSendingService {
    fun sendMail(mail: Mail, mailLogId: MailLogId)
}
