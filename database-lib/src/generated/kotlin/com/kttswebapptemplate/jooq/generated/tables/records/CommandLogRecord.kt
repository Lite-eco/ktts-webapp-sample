/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables.records

import com.kttswebapptemplate.jooq.generated.tables.CommandLogTable
import java.time.Instant
import java.util.UUID
import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record13
import org.jooq.Row13
import org.jooq.impl.UpdatableRecordImpl

/** This class is generated by jOOQ. */
@Suppress("UNCHECKED_CAST")
open class CommandLogRecord private constructor() :
    UpdatableRecordImpl<CommandLogRecord>(CommandLogTable.COMMAND_LOG),
    Record13<
        UUID?,
        UUID?,
        UUID?,
        UUID?,
        String?,
        String?,
        String?,
        UUID?,
        String?,
        String?,
        String?,
        Instant?,
        Instant?> {

    open var id: UUID
        set(value): Unit = set(0, value)
        get(): UUID = get(0) as UUID

    open var userId: UUID?
        set(value): Unit = set(1, value)
        get(): UUID? = get(1) as UUID?

    open var affectedUserId: UUID?
        set(value): Unit = set(2, value)
        get(): UUID? = get(2) as UUID?

    open var deploymentLogId: UUID
        set(value): Unit = set(3, value)
        get(): UUID = get(3) as UUID

    open var commandClass: String
        set(value): Unit = set(4, value)
        get(): String = get(4) as String

    open var jsonCommand: String
        set(value): Unit = set(5, value)
        get(): String = get(5) as String

    open var ip: String
        set(value): Unit = set(6, value)
        get(): String = get(6) as String

    open var userSessionId: UUID?
        set(value): Unit = set(7, value)
        get(): UUID? = get(7) as UUID?

    open var idsLog: String
        set(value): Unit = set(8, value)
        get(): String = get(8) as String

    open var jsonResult: String?
        set(value): Unit = set(9, value)
        get(): String? = get(9) as String?

    open var exceptionStackTrace: String?
        set(value): Unit = set(10, value)
        get(): String? = get(10) as String?

    open var startDate: Instant
        set(value): Unit = set(11, value)
        get(): Instant = get(11) as Instant

    open var endDate: Instant
        set(value): Unit = set(12, value)
        get(): Instant = get(12) as Instant

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow():
        Row13<
            UUID?,
            UUID?,
            UUID?,
            UUID?,
            String?,
            String?,
            String?,
            UUID?,
            String?,
            String?,
            String?,
            Instant?,
            Instant?> =
        super.fieldsRow()
            as
            Row13<
                UUID?,
                UUID?,
                UUID?,
                UUID?,
                String?,
                String?,
                String?,
                UUID?,
                String?,
                String?,
                String?,
                Instant?,
                Instant?>
    override fun valuesRow():
        Row13<
            UUID?,
            UUID?,
            UUID?,
            UUID?,
            String?,
            String?,
            String?,
            UUID?,
            String?,
            String?,
            String?,
            Instant?,
            Instant?> =
        super.valuesRow()
            as
            Row13<
                UUID?,
                UUID?,
                UUID?,
                UUID?,
                String?,
                String?,
                String?,
                UUID?,
                String?,
                String?,
                String?,
                Instant?,
                Instant?>
    override fun field1(): Field<UUID?> = CommandLogTable.COMMAND_LOG.ID
    override fun field2(): Field<UUID?> = CommandLogTable.COMMAND_LOG.USER_ID
    override fun field3(): Field<UUID?> = CommandLogTable.COMMAND_LOG.AFFECTED_USER_ID
    override fun field4(): Field<UUID?> = CommandLogTable.COMMAND_LOG.DEPLOYMENT_LOG_ID
    override fun field5(): Field<String?> = CommandLogTable.COMMAND_LOG.COMMAND_CLASS
    override fun field6(): Field<String?> = CommandLogTable.COMMAND_LOG.JSON_COMMAND
    override fun field7(): Field<String?> = CommandLogTable.COMMAND_LOG.IP
    override fun field8(): Field<UUID?> = CommandLogTable.COMMAND_LOG.USER_SESSION_ID
    override fun field9(): Field<String?> = CommandLogTable.COMMAND_LOG.IDS_LOG
    override fun field10(): Field<String?> = CommandLogTable.COMMAND_LOG.JSON_RESULT
    override fun field11(): Field<String?> = CommandLogTable.COMMAND_LOG.EXCEPTION_STACK_TRACE
    override fun field12(): Field<Instant?> = CommandLogTable.COMMAND_LOG.START_DATE
    override fun field13(): Field<Instant?> = CommandLogTable.COMMAND_LOG.END_DATE
    override fun component1(): UUID = id
    override fun component2(): UUID? = userId
    override fun component3(): UUID? = affectedUserId
    override fun component4(): UUID = deploymentLogId
    override fun component5(): String = commandClass
    override fun component6(): String = jsonCommand
    override fun component7(): String = ip
    override fun component8(): UUID? = userSessionId
    override fun component9(): String = idsLog
    override fun component10(): String? = jsonResult
    override fun component11(): String? = exceptionStackTrace
    override fun component12(): Instant = startDate
    override fun component13(): Instant = endDate
    override fun value1(): UUID = id
    override fun value2(): UUID? = userId
    override fun value3(): UUID? = affectedUserId
    override fun value4(): UUID = deploymentLogId
    override fun value5(): String = commandClass
    override fun value6(): String = jsonCommand
    override fun value7(): String = ip
    override fun value8(): UUID? = userSessionId
    override fun value9(): String = idsLog
    override fun value10(): String? = jsonResult
    override fun value11(): String? = exceptionStackTrace
    override fun value12(): Instant = startDate
    override fun value13(): Instant = endDate

    override fun value1(value: UUID?): CommandLogRecord {
        set(0, value)
        return this
    }

    override fun value2(value: UUID?): CommandLogRecord {
        set(1, value)
        return this
    }

    override fun value3(value: UUID?): CommandLogRecord {
        set(2, value)
        return this
    }

    override fun value4(value: UUID?): CommandLogRecord {
        set(3, value)
        return this
    }

    override fun value5(value: String?): CommandLogRecord {
        set(4, value)
        return this
    }

    override fun value6(value: String?): CommandLogRecord {
        set(5, value)
        return this
    }

    override fun value7(value: String?): CommandLogRecord {
        set(6, value)
        return this
    }

    override fun value8(value: UUID?): CommandLogRecord {
        set(7, value)
        return this
    }

    override fun value9(value: String?): CommandLogRecord {
        set(8, value)
        return this
    }

    override fun value10(value: String?): CommandLogRecord {
        set(9, value)
        return this
    }

    override fun value11(value: String?): CommandLogRecord {
        set(10, value)
        return this
    }

    override fun value12(value: Instant?): CommandLogRecord {
        set(11, value)
        return this
    }

    override fun value13(value: Instant?): CommandLogRecord {
        set(12, value)
        return this
    }

    override fun values(
        value1: UUID?,
        value2: UUID?,
        value3: UUID?,
        value4: UUID?,
        value5: String?,
        value6: String?,
        value7: String?,
        value8: UUID?,
        value9: String?,
        value10: String?,
        value11: String?,
        value12: Instant?,
        value13: Instant?
    ): CommandLogRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        this.value6(value6)
        this.value7(value7)
        this.value8(value8)
        this.value9(value9)
        this.value10(value10)
        this.value11(value11)
        this.value12(value12)
        this.value13(value13)
        return this
    }

    /** Create a detached, initialised CommandLogRecord */
    constructor(
        id: UUID,
        userId: UUID? = null,
        affectedUserId: UUID? = null,
        deploymentLogId: UUID,
        commandClass: String,
        jsonCommand: String,
        ip: String,
        userSessionId: UUID? = null,
        idsLog: String,
        jsonResult: String? = null,
        exceptionStackTrace: String? = null,
        startDate: Instant,
        endDate: Instant
    ) : this() {
        this.id = id
        this.userId = userId
        this.affectedUserId = affectedUserId
        this.deploymentLogId = deploymentLogId
        this.commandClass = commandClass
        this.jsonCommand = jsonCommand
        this.ip = ip
        this.userSessionId = userSessionId
        this.idsLog = idsLog
        this.jsonResult = jsonResult
        this.exceptionStackTrace = exceptionStackTrace
        this.startDate = startDate
        this.endDate = endDate
        resetChangedOnNotNull()
    }
}
