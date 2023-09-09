package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.domain.UserAccountOperationToken
import com.kttswebapptemplate.domain.UserAccountOperationTokenType
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserMailLogId
import com.kttswebapptemplate.jooq.generated.tables.records.UserAccountOperationTokenRecord
import com.kttswebapptemplate.jooq.generated.tables.references.USER_ACCOUNT_OPERATION_TOKEN
import com.kttswebapptemplate.utils.toSecurityString
import com.kttswebapptemplate.utils.toTypeId
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserAccountOperationTokenDao(private val jooq: DSLContext) {

    data class Record(
        val token: UserAccountOperationToken,
        val tokenType: UserAccountOperationTokenType,
        val userId: UserId,
        val userMailLogId: UserMailLogId?,
        val creationDate: Instant
    )

    fun insert(r: Record) {
        jooq
            .insertInto(USER_ACCOUNT_OPERATION_TOKEN)
            .set(
                UserAccountOperationTokenRecord(
                    token = r.token.rawString,
                    tokenType = r.tokenType.name,
                    userId = r.userId.rawId,
                    userMailLogId = r.userMailLogId?.rawId,
                    creationDate = r.creationDate))
            .execute()
    }

    fun fetchOrNull(
        token: UserAccountOperationToken,
        tokenType: UserAccountOperationTokenType
    ): Record? =
        jooq
            .selectFrom(USER_ACCOUNT_OPERATION_TOKEN)
            .where(USER_ACCOUNT_OPERATION_TOKEN.TOKEN.equal(token.rawString))
            .and(USER_ACCOUNT_OPERATION_TOKEN.TOKEN_TYPE.equal(tokenType.name))
            .fetchOne()
            ?.let(this::map)

    private fun map(r: UserAccountOperationTokenRecord) =
        Record(
            token = r.token.toSecurityString(),
            tokenType = UserAccountOperationTokenType.valueOf(r.tokenType),
            userId = r.userId.toTypeId(),
            userMailLogId = r.userMailLogId?.toTypeId(),
            creationDate = r.creationDate)
}
