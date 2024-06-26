package com.kttswebapptemplate.repository.log

import com.kttswebapptemplate.domain.CommandLogId
import com.kttswebapptemplate.domain.DeploymentLogId
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserSessionId
import com.kttswebapptemplate.jooq.generated.tables.records.CommandLogRecord
import com.kttswebapptemplate.jooq.generated.tables.references.COMMAND_LOG
import com.kttswebapptemplate.utils.toTypeId
import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class CommandLogDao(private val jooq: DSLContext) {

    data class Record(
        val id: CommandLogId,
        val userId: UserId?,
        val affectedUserId: UserId?,
        val deploymentLogId: DeploymentLogId,
        val commandClass: String,
        val jsonCommand: String,
        val ip: String,
        val userSessionId: UserSessionId?,
        val idsLog: String,
        val jsonResult: String?,
        val exceptionStackTrace: String?,
        val startDate: Instant,
        val endDate: Instant
    )

    fun insert(r: Record) {
        jooq
            .insertInto(COMMAND_LOG)
            .set(
                CommandLogRecord(
                    id = r.id.rawId,
                    userId = r.userId?.rawId,
                    affectedUserId = r.affectedUserId?.rawId,
                    deploymentLogId = r.deploymentLogId.rawId,
                    commandClass = r.commandClass,
                    jsonCommand = r.jsonCommand,
                    ip = r.ip,
                    userSessionId = r.userSessionId?.rawId,
                    idsLog = r.idsLog,
                    jsonResult = r.jsonResult,
                    exceptionStackTrace = r.exceptionStackTrace,
                    startDate = r.startDate,
                    endDate = r.endDate,
                ))
            .execute()
    }

    private fun map(r: CommandLogRecord) =
        Record(
            id = r.id.toTypeId(),
            userId = r.userId?.toTypeId(),
            affectedUserId = r.affectedUserId?.toTypeId(),
            deploymentLogId = r.deploymentLogId.toTypeId(),
            // Class.forName() is not a good idea here, command classes change in time
            commandClass = r.commandClass,
            jsonCommand = r.jsonCommand,
            ip = r.ip,
            userSessionId = r.userSessionId?.toTypeId(),
            idsLog = r.idsLog,
            jsonResult = r.jsonResult,
            exceptionStackTrace = r.exceptionStackTrace,
            startDate = r.startDate,
            endDate = r.endDate)
}
