package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.domain.AuthLogType
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserMailLogId
import com.kttswebapptemplate.jooq.generated.Tables.USER_MAIL_LOG
import com.kttswebapptemplate.jooq.generated.tables.records.UserMailLogRecord
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserMailLogDao(private val jooq: DSLContext) {

    data class Record(
        val id: UserMailLogId,
        val userId: UserId,
        val mail: String,
        val type: AuthLogType,
        val creationDate: Instant
    )

    fun insert(r: Record) {
        val jr =
            UserMailLogRecord().apply {
                id = r.id.rawId
                userId = r.userId.rawId
                mail = r.mail
                type = r.type.name
                creationDate = r.creationDate
            }

        jooq.insertInto(USER_MAIL_LOG).set(jr).execute()
    }
}
