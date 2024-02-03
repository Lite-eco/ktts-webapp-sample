package com.kttswebapptemplate.repository.log

import com.kttswebapptemplate.domain.MailLogId
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.jooq.generated.tables.records.MailingLogRecord
import com.kttswebapptemplate.jooq.generated.tables.references.MAILING_LOG
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class MailingLogDao(private val jooq: DSLContext) {

    data class Record(
        val id: MailLogId,
        val userId: UserId?,
        val senderName: String,
        val senderMail: String,
        val recipientName: String,
        val recipientMail: String,
        val subject: String,
        val content: String,
        val data: String,
        val logDate: Instant
    )

    fun insert(r: Record) {
        jooq
            .insertInto(MAILING_LOG)
            .set(
                MailingLogRecord(
                    id = r.id.rawId,
                    userId = r.userId?.rawId,
                    senderName = r.senderName,
                    senderMail = r.senderMail,
                    recipientName = r.recipientName,
                    recipientMail = r.recipientMail,
                    subject = r.subject,
                    content = r.content,
                    data = r.data,
                    logDate = r.logDate))
            .execute()
    }
}
