package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.domain.DeploymentLogId
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserSessionId
import com.kttswebapptemplate.jooq.generated.Tables.USER_SESSION_LOG
import com.kttswebapptemplate.jooq.generated.tables.records.UserSessionLogRecord
import com.kttswebapptemplate.utils.toTypeId
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

// TODO[tmpl][user]: try to keep Spring id
@Repository
class UserSessionLogDao(private val jooq: DSLContext) {

    data class Record(
        val id: UserSessionId,
        val springSessionId: String,
        val userId: UserId,
        val deploymentLogId: DeploymentLogId,
        val creationDate: Instant,
        val ip: String
    )

    fun insert(r: Record) {
        val jr =
            UserSessionLogRecord().apply {
                id = r.id.rawId
                springSessionId = r.springSessionId
                userId = r.userId.rawId
                deploymentLogId = r.deploymentLogId.rawId
                creationDate = r.creationDate
                ip = r.ip
            }
        jooq.insertInto(USER_SESSION_LOG).set(jr).execute()
    }

    fun fetchIdsByUserId(userId: UserId): List<UserSessionId> =
        jooq
            .select(USER_SESSION_LOG.ID)
            .from(USER_SESSION_LOG)
            .where(USER_SESSION_LOG.USER_ID.equal(userId.rawId))
            .fetch()
            .map { it.component1().toTypeId() }
}
