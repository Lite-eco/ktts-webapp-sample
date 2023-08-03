package templatesample.repository.user

import templatesample.domain.AuthLogId
import templatesample.domain.AuthLogType
import templatesample.domain.UserId
import templatesample.jooq.generated.Tables.AUTH_LOG
import templatesample.jooq.generated.tables.records.AuthLogRecord
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class AuthLogDao(val jooq: DSLContext) {

    data class Record(
        val id: AuthLogId,
        val userId: UserId,
        val mail: String,
        val type: AuthLogType,
        val creationDate: Instant
    ) {
        override fun toString() = "User($id|$mail)"
    }

    fun insert(r: Record) {
        val jr =
            AuthLogRecord().apply {
                id = r.id.rawId
                userId = r.userId.rawId
                mail = r.mail
                type = r.type.name
                creationDate = r.creationDate
            }

        jooq.insertInto(AUTH_LOG).set(jr).execute()
    }
}
