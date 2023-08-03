package com.kttswebapptemplate.repository.log

import com.kttswebapptemplate.domain.DeploymentLogId
import com.kttswebapptemplate.domain.MailLogId
import com.kttswebapptemplate.domain.MailReference
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.jooq.generated.tables.records.MailLogRecord
import com.kttswebapptemplate.jooq.generated.tables.references.MAIL_LOG
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class MailLogDao(private val jooq: DSLContext) {

    data class Record(
        val id: MailLogId,
        val deploymentLogId: DeploymentLogId,
        val userId: UserId,
        val reference: MailReference,
        val recipientMail: String,
        val data: String,
        val subject: String,
        val content: String,
        val date: Instant
    )

    fun insert(r: Record) {
        jooq
            .insertInto(MAIL_LOG)
            .set(
                MailLogRecord(
                    id = r.id.rawId,
                    deploymentLogId = r.deploymentLogId.rawId,
                    userId = r.userId.rawId,
                    reference = r.reference.name,
                    recipientMail = r.recipientMail,
                    data = r.data,
                    subject = r.subject,
                    content = r.content,
                    date = r.date))
            .execute()
    }
}
