package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.jooq.generated.Tables.MAGIC_LINK_TOKEN
import com.kttswebapptemplate.jooq.generated.tables.records.MagicLinkTokenRecord
import com.kttswebapptemplate.utils.toTypeId
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class MagicLinkTokenDao(private val jooq: DSLContext) {

    data class Record(
        val token: String,
        val userId: UserId,
        val validity: Boolean,
        val creationDate: Instant,
        val lastUpdate: Instant
    )

    fun insert(r: Record) {
        val jr =
            MagicLinkTokenRecord().apply {
                token = r.token
                userId = r.userId.rawId
                validity = r.validity
                creationDate = r.creationDate
                lastUpdate = r.lastUpdate
            }
        jooq.insertInto(MAGIC_LINK_TOKEN).set(jr).execute()
    }

    fun fetchOrNull(magicToken: String): Record? =
        jooq
            .selectFrom(MAGIC_LINK_TOKEN)
            .where(MAGIC_LINK_TOKEN.TOKEN.equal(magicToken))
            .fetchOne()
            ?.let(this::map)

    fun updateValidity(token: String, validity: Boolean, lastUpdateDate: Instant) {
        jooq
            .update(MAGIC_LINK_TOKEN)
            .set(MAGIC_LINK_TOKEN.VALIDITY, validity)
            .set(MAGIC_LINK_TOKEN.LAST_UPDATE, lastUpdateDate)
            .where(MAGIC_LINK_TOKEN.TOKEN.equal(token))
            .execute()
    }

    private fun map(r: MagicLinkTokenRecord) =
        Record(
            token = r.token,
            userId = r.userId.toTypeId(),
            validity = r.validity,
            creationDate = r.creationDate,
            lastUpdate = r.lastUpdate)
}