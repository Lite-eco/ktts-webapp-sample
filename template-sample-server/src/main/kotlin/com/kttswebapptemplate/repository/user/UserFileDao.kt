package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.domain.MimeType
import com.kttswebapptemplate.domain.UserFileId
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.jooq.generated.tables.records.UserFileRecord
import com.kttswebapptemplate.jooq.generated.tables.references.USER_FILE
import com.kttswebapptemplate.utils.toTypeId
import java.time.Instant
import org.jooq.DSLContext
import org.jooq.Record as JooqRecord
import org.springframework.stereotype.Repository

@Repository
class UserFileDao(private val jooq: DSLContext) {

    companion object {
        val nonDataFields by lazy {
            setOf(
                USER_FILE.ID,
                USER_FILE.USER_ID,
                USER_FILE.CONTENT_TYPE,
                USER_FILE.ORIGINAL_FILENAME,
                USER_FILE.UPLOAD_DATE)
        }
    }

    data class Record(
        val id: UserFileId,
        val userId: UserId,
        val contentType: MimeType,
        val originalFilename: String,
        val uploadDate: Instant
    )

    fun insert(r: Record, bytes: ByteArray) {
        jooq
            .insertInto(USER_FILE)
            .set(
                UserFileRecord(
                    id = r.id.rawId,
                    userId = r.userId.rawId,
                    fileContent = bytes,
                    contentType = r.contentType.type,
                    originalFilename = r.originalFilename,
                    uploadDate = r.uploadDate))
            .execute()
    }

    fun fetchDataOrNull(id: UserFileId): Pair<Record, ByteArray>? =
        jooq.selectFrom(USER_FILE).where(USER_FILE.ID.equal(id.rawId)).fetchOne()?.let {
            mapRecord(it) to it.fileContent
        }

    fun fetchReferenceOrNull(id: UserFileId): Record? =
        jooq
            .select(nonDataFields)
            .from(USER_FILE)
            .where(USER_FILE.ID.equal(id.rawId))
            .fetchOne()
            ?.let { mapRecord(it) }

    fun fetchReferencesByUserId(userId: UserId): List<Record> =
        jooq
            .select(nonDataFields)
            .from(USER_FILE)
            .where(USER_FILE.USER_ID.equal(userId.rawId))
            .toList()
            .map { mapRecord(it.into(UserFileRecord::class.java)) }

    fun count(): Int = jooq.selectCount().from(USER_FILE).fetchSingle().value1()

    fun mapRecord(raw: JooqRecord): Record {
        val r = raw.into(UserFileRecord::class.java)
        return Record(
            id = r.id.toTypeId(),
            userId = r.userId.toTypeId(),
            contentType = MimeType(r.contentType),
            originalFilename = r.originalFilename,
            uploadDate = r.uploadDate)
    }
}
