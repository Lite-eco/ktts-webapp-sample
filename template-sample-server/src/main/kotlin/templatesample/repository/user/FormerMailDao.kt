package templatesample.repository.user

import templatesample.domain.FormerMailId
import templatesample.domain.UserId
import templatesample.jooq.generated.Tables.FORMER_MAIL
import templatesample.jooq.generated.tables.records.FormerMailRecord
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class FormerMailDao(val jooq: DSLContext) {

    data class Record(
        val id: FormerMailId,
        val userId: UserId,
        val mail: String,
        val dirtyMail: String?,
        val creationDate: Instant
    ) {
        override fun toString() = "User($id|$mail)"
    }

    fun insert(r: Record) {
        val jr =
            FormerMailRecord().apply {
                id = r.id.rawId
                userId = r.userId.rawId
                mail = r.mail
                mail = r.mail
                dirtyMail = r.dirtyMail
                creationDate = r.creationDate
            }

        jooq.insertInto(FORMER_MAIL).set(jr).execute()
    }
}
