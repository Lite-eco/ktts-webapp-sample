package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.domain.HashedPassword
import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserStatus
import com.kttswebapptemplate.error.MailAlreadyRegisteredException
import com.kttswebapptemplate.jooq.generated.keys.APP_USER_MAIL_KEY
import com.kttswebapptemplate.jooq.generated.tables.records.AppUserRecord
import com.kttswebapptemplate.jooq.generated.tables.references.APP_USER
import com.kttswebapptemplate.utils.toTypeId
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

@Repository
class UserDao(private val jooq: DSLContext) {

    data class Record(
        val id: UserId,
        val mail: String,
        val displayName: String,
        val language: Language,
        val status: UserStatus,
        val role: Role,
        val signupDate: Instant,
        val lastUpdateDate: Instant
    ) {
        override fun toString() = "User($id|$mail)"
    }

    fun insert(r: Record, hashedPassword: HashedPassword) {
        try {
            jooq
                .insertInto(APP_USER)
                .set(
                    AppUserRecord(
                        id = r.id.rawId,
                        mail = r.mail,
                        password = hashedPassword.hash,
                        displayName = r.displayName,
                        language = r.language.name,
                        status = r.status.name,
                        role = r.role.name,
                        signupDate = r.signupDate,
                        lastUpdateDate = r.lastUpdateDate))
                .execute()
        } catch (e: DuplicateKeyException) {
            handleDuplicateKeyException(e, r.mail)
        }
    }

    fun updateMail(id: UserId, mail: String, lastUpdateDate: Instant) {
        try {
            jooq
                .update(APP_USER)
                .set(APP_USER.MAIL, mail)
                .set(APP_USER.LAST_UPDATE_DATE, lastUpdateDate)
                .where(APP_USER.ID.equal(id.rawId))
                .execute()
        } catch (e: DuplicateKeyException) {
            handleDuplicateKeyException(e, mail)
        }
    }

    private fun handleDuplicateKeyException(e: DuplicateKeyException, mail: String) {
        val message = e.cause?.message
        if (message != null &&
            "duplicate key value violates unique constraint \"" + APP_USER_MAIL_KEY.name + "\"" in
                message) {
            throw MailAlreadyRegisteredException(mail)
        } else {
            throw e
        }
    }

    fun updatePassword(id: UserId, password: HashedPassword, lastUpdateDate: Instant) {
        jooq
            .update(APP_USER)
            .set(APP_USER.PASSWORD, password.hash)
            .set(APP_USER.LAST_UPDATE_DATE, lastUpdateDate)
            .where(APP_USER.ID.equal(id.rawId))
            .execute()
    }

    fun updateRole(id: UserId, role: Role, lastUpdateDate: Instant) {
        jooq
            .update(APP_USER)
            .set(APP_USER.ROLE, role.name)
            .set(APP_USER.LAST_UPDATE_DATE, lastUpdateDate)
            .where(APP_USER.ID.equal(id.rawId))
            .execute()
    }

    fun updateStatus(id: UserId, status: UserStatus, lastUpdateDate: Instant) {
        jooq
            .update(APP_USER)
            .set(APP_USER.STATUS, status.name)
            .set(APP_USER.LAST_UPDATE_DATE, lastUpdateDate)
            .where(APP_USER.ID.equal(id.rawId))
            .execute()
    }

    fun doesMailExist(mail: String): Boolean =
        jooq.selectCount().from(APP_USER).where(APP_USER.MAIL.equal(mail)).fetchSingle().value1() >
            0

    fun fetch(id: UserId): Record = requireNotNull(fetchOrNull(id)) { "$id" }

    fun fetchOrNull(id: UserId): Record? =
        jooq.selectFrom(APP_USER).where(APP_USER.ID.equal(id.rawId)).fetchOne()?.let(this::map)

    fun fetchByMail(mail: String): Record = requireNotNull(fetchOrNullByMail(mail)) { mail }

    fun fetchOrNullByMail(mail: String): Record? =
        jooq.selectFrom(APP_USER).where(APP_USER.MAIL.equal(mail)).fetchOne()?.let(this::map)

    fun fetchPassword(id: UserId): HashedPassword =
        jooq
            .select(APP_USER.PASSWORD)
            .from(APP_USER)
            .where(APP_USER.ID.equal(id.rawId))
            .fetchOne()
            ?.value1()
            ?.let { HashedPassword(it) }
            .let { requireNotNull(it) { "$id" } }

    fun fetchMail(id: UserId): String =
        jooq
            .select(APP_USER.MAIL)
            .from(APP_USER)
            .where(APP_USER.ID.equal(id.rawId))
            .fetchOne()
            ?.value1()
            .let { requireNotNull(it) { "$id" } }

    fun fetchAll(): List<Record> = jooq.selectFrom(APP_USER).fetch().map(this::map)

    fun map(r: AppUserRecord) =
        Record(
            id = r.id.toTypeId(),
            mail = r.mail,
            displayName = r.displayName,
            language = Language.valueOf(r.language),
            status = UserStatus.valueOf(r.status),
            role = Role.valueOf(r.role),
            signupDate = r.signupDate,
            lastUpdateDate = r.lastUpdateDate)
}
