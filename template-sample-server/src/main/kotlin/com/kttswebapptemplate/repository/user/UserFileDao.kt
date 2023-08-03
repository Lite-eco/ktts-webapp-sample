package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.domain.MimeType
import com.kttswebapptemplate.domain.UserFileData
import com.kttswebapptemplate.domain.UserFileId
import com.kttswebapptemplate.domain.UserFileReference
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.jooq.generated.Tables.USER_FILE
import com.kttswebapptemplate.jooq.generated.tables.records.UserFileRecord
import com.kttswebapptemplate.utils.toTypeId
import org.jooq.DSLContext
import org.jooq.Record
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

    fun insert(r: UserFileReference, bytes: ByteArray) {
        // FIMENOW refactor proposition
        val jr =
            UserFileRecord().apply {
                id = r.id.rawId
                userId = r.userId.rawId
                fileContent = bytes
                contentType = r.contentType.fullType
                originalFilename = r.originalFilename
                uploadDate = r.uploadDate
            }
        jooq.insertInto(USER_FILE).set(jr).execute()
    }

    fun fetchDataOrNull(id: UserFileId): UserFileData? =
        jooq
            .select(USER_FILE.CONTENT_TYPE, USER_FILE.FILE_CONTENT)
            .from(USER_FILE)
            .where(USER_FILE.ID.equal(id.rawId))
            .fetchOne()
            ?.let { mapData(it.into(USER_FILE)) }

    fun fetchReferenceOrNull(id: UserFileId): UserFileReference? =
        jooq
            .select(nonDataFields)
            .from(USER_FILE)
            .where(USER_FILE.ID.equal(id.rawId))
            .fetchOne()
            ?.let { mapReference(it) }

    fun fetchReferencesByUserId(userId: UserId): List<UserFileReference> =
        jooq
            .select(nonDataFields)
            .from(USER_FILE)
            .where(USER_FILE.USER_ID.equal(userId.rawId))
            .toList()
            .map { mapReference(it) }

    fun count(): Int = jooq.selectCount().from(USER_FILE).fetchSingle().value1()

    fun mapData(record: Record): UserFileData {
        val r = record.into(UserFileRecord::class.java)
        return UserFileData(r.contentType, r.fileContent)
    }

    fun mapReference(record: Record): UserFileReference {
        val r = record.into(UserFileRecord::class.java)
        return UserFileReference(
            id = r.id.toTypeId(),
            userId = r.userId.toTypeId(),
            contentType = MimeType.of(r.contentType),
            originalFilename = r.originalFilename,
            uploadDate = r.uploadDate)
    }
}
