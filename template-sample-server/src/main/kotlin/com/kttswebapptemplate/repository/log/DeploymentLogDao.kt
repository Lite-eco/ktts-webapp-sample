package com.kttswebapptemplate.repository.log

import com.kttswebapptemplate.domain.DeploymentLogId
import com.kttswebapptemplate.jooq.generated.tables.records.DeploymentLogRecord
import com.kttswebapptemplate.jooq.generated.tables.references.DEPLOYMENT_LOG
import java.time.Instant
import java.time.ZoneId
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class DeploymentLogDao(private val jooq: DSLContext) {

    // is better than a simple log in a distributed environment
    data class Record(
        val id: DeploymentLogId,
        val buildVersion: String,
        val systemZoneId: ZoneId,
        val startupDate: Instant,
        val shutdownDate: Instant?
    )

    fun insert(r: Record) {
        jooq
            .insertInto(DEPLOYMENT_LOG)
            .set(
                DeploymentLogRecord(
                    id = r.id.rawId,
                    buildVersion = r.buildVersion,
                    systemZoneId = r.systemZoneId.id,
                    startupDate = r.startupDate))
            .execute()
    }

    fun updateShutdownTime(id: DeploymentLogId, shutdownDate: Instant) =
        jooq
            .update(DEPLOYMENT_LOG)
            .set(DEPLOYMENT_LOG.SHUTDOWN_DATE, shutdownDate)
            .where(DEPLOYMENT_LOG.ID.equal(id.rawId))
            .execute()
}
