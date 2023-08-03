package templatesample.repository.log

import java.time.Instant
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import templatesample.domain.CommandLogId
import templatesample.domain.DeploymentLogId
import templatesample.domain.UserId
import templatesample.domain.UserSessionId
import templatesample.jooq.generated.Tables.COMMAND_LOG
import templatesample.jooq.generated.tables.records.CommandLogRecord
import templatesample.utils.toTypeId

@Repository
class CommandLogDao(private val jooq: DSLContext) {

    data class Record(
        val id: CommandLogId,
        val userId: UserId?,
        val deploymentLogId: DeploymentLogId,
        val commandClass: Class<*>,
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
        val jr =
            CommandLogRecord().apply {
                id = r.id.rawId
                userId = r.userId?.rawId
                deploymentLogId = r.deploymentLogId.rawId
                commandClass = r.commandClass.name
                jsonCommand = r.jsonCommand
                ip = r.ip
                userSessionId = r.userSessionId?.rawId
                idsLog = r.idsLog
                jsonResult = r.jsonResult
                exceptionStackTrace = r.exceptionStackTrace
                startDate = r.startDate
                endDate = r.endDate
            }
        jooq.insertInto(COMMAND_LOG).set(jr).execute()
    }

    private fun map(r: CommandLogRecord) =
        Record(
            id = r.id.toTypeId(),
            userId = r.userId?.toTypeId(),
            deploymentLogId = r.deploymentLogId.toTypeId(),
            commandClass = Class.forName(r.commandClass),
            jsonCommand = r.jsonCommand,
            ip = r.ip,
            userSessionId = r.userSessionId?.toTypeId(),
            idsLog = r.idsLog,
            jsonResult = r.jsonResult,
            exceptionStackTrace = r.exceptionStackTrace,
            startDate = r.startDate,
            endDate = r.endDate)
}
