package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserMailLogId
import com.kttswebapptemplate.jooq.generated.tables.records.UserMailLogRecord
import com.kttswebapptemplate.jooq.generated.tables.references.USER_MAIL_LOG
import com.kttswebapptemplate.utils.toTypeId
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserMailLogDao(private val jooq: DSLContext) {

    data class Record(
        val id: UserMailLogId,
        val userId: UserId,
        val mail: String,
        val dirtyMail: String?,
        val validated: Boolean,
        val creationDate: Instant
    )

    fun insert(r: Record) {
        jooq
            .insertInto(USER_MAIL_LOG)
            .set(
                UserMailLogRecord(
                    id = r.id.rawId,
                    userId = r.userId.rawId,
                    mail = r.mail,
                    dirtyMail = r.dirtyMail,
                    validated = r.validated,
                    creationDate = r.creationDate))
            .execute()
    }

    fun fetchLastByUserId(userId: UserId) =
        jooq
            .selectFrom(USER_MAIL_LOG)
            .where(USER_MAIL_LOG.USER_ID.equal(userId.rawId))
            .orderBy(USER_MAIL_LOG.CREATION_DATE.desc())
            .limit(1)
            .fetchSingle()
            .let(this::map)

    fun map(r: UserMailLogRecord) =
        Record(
            id = r.id.toTypeId(),
            userId = r.userId.toTypeId(),
            mail = r.mail,
            dirtyMail = r.dirtyMail,
            validated = r.validated,
            creationDate = r.creationDate)
}
