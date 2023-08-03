package templatesample.repository.log

import templatesample.domain.DeploymentLogId
import templatesample.domain.MailLogId
import templatesample.domain.MailReference
import templatesample.domain.UserId
import templatesample.jooq.generated.Tables.MAIL_LOG
import templatesample.jooq.generated.tables.records.MailLogRecord
import templatesample.utils.toTypeId
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

    data class HistoryPartialRecord(
        val id: MailLogId,
        val reference: MailReference,
        val subject: String,
        val date: Instant
    )

    data class ContentPartialRecord(val userId: UserId, val content: String)

    val historyPartialRecordFields =
        arrayOf(MAIL_LOG.ID, MAIL_LOG.REFERENCE, MAIL_LOG.SUBJECT, MAIL_LOG.DATE)

    val contentPartialRecordFields = arrayOf(MAIL_LOG.USER_ID, MAIL_LOG.CONTENT)

    fun insert(r: Record) {
        val jr =
            MailLogRecord().apply {
                id = r.id.rawId
                deploymentLogId = r.deploymentLogId.rawId
                userId = r.userId.rawId
                reference = r.reference.name
                recipientMail = r.recipientMail
                data = r.data
                subject = r.subject
                content = r.content
                date = r.date
            }
        jooq.insertInto(MAIL_LOG).set(jr).execute()
    }

    fun fetchAll(): List<Record> = jooq.selectFrom(MAIL_LOG).fetch().map(this::map)

    fun fetchContentOrNull(id: MailLogId): ContentPartialRecord? =
        jooq
            .select(*contentPartialRecordFields)
            .from(MAIL_LOG)
            .where(MAIL_LOG.ID.equal(id.rawId))
            .fetchOne()
            ?.let(this::mapContentPartialRecord)

    fun fetchByUserIdAndReferences(
        userId: UserId,
        mailReferences: List<MailReference>
    ): List<Record> =
        jooq
            .selectFrom(MAIL_LOG)
            .where(MAIL_LOG.USER_ID.equal(userId.rawId))
            .and(MAIL_LOG.REFERENCE.`in`(mailReferences.map { it.name }))
            .fetch()
            .map(this::map)

    fun fetchHistoryPartialRecordByUserIdAndReferences(
        userId: UserId,
        mailReferences: Set<MailReference>
    ): List<HistoryPartialRecord> =
        jooq
            .select(*historyPartialRecordFields)
            .from(MAIL_LOG)
            .where(MAIL_LOG.USER_ID.equal(userId.rawId))
            .and(MAIL_LOG.REFERENCE.`in`(mailReferences.map { it.name }))
            .fetch()
            .map(this::mapHistoryPartialRecord)

    fun map(r: MailLogRecord) =
        Record(
            id = r.id.toTypeId(),
            deploymentLogId = r.deploymentLogId.toTypeId(),
            userId = r.userId.toTypeId(),
            reference = MailReference.valueOf(r.reference),
            recipientMail = r.recipientMail,
            data = r.data,
            subject = r.subject,
            content = r.content,
            date = r.date)

    fun mapHistoryPartialRecord(r: org.jooq.Record) =
        HistoryPartialRecord(
            r.get(MAIL_LOG.ID).toTypeId(),
            MailReference.valueOf(r.get(MAIL_LOG.REFERENCE)),
            r.get(MAIL_LOG.SUBJECT),
            r.get(MAIL_LOG.DATE))

    fun mapContentPartialRecord(r: org.jooq.Record) =
        ContentPartialRecord(r.get(MAIL_LOG.USER_ID).toTypeId(), r.get(MAIL_LOG.CONTENT))
}
