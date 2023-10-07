/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables.records

import com.kttswebapptemplate.jooq.generated.tables.DeploymentLogTable
import java.time.Instant
import java.util.UUID
import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record5
import org.jooq.Row5
import org.jooq.impl.UpdatableRecordImpl

/** This class is generated by jOOQ. */
@Suppress("UNCHECKED_CAST")
open class DeploymentLogRecord private constructor() :
    UpdatableRecordImpl<DeploymentLogRecord>(DeploymentLogTable.DEPLOYMENT_LOG),
    Record5<UUID?, String?, String?, Instant?, Instant?> {

    open var id: UUID
        set(value): Unit = set(0, value)
        get(): UUID = get(0) as UUID

    open var buildVersion: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var systemZoneId: String
        set(value): Unit = set(2, value)
        get(): String = get(2) as String

    open var startupDate: Instant
        set(value): Unit = set(3, value)
        get(): Instant = get(3) as Instant

    open var shutdownDate: Instant?
        set(value): Unit = set(4, value)
        get(): Instant? = get(4) as Instant?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row5<UUID?, String?, String?, Instant?, Instant?> =
        super.fieldsRow() as Row5<UUID?, String?, String?, Instant?, Instant?>

    override fun valuesRow(): Row5<UUID?, String?, String?, Instant?, Instant?> =
        super.valuesRow() as Row5<UUID?, String?, String?, Instant?, Instant?>

    override fun field1(): Field<UUID?> = DeploymentLogTable.DEPLOYMENT_LOG.ID

    override fun field2(): Field<String?> = DeploymentLogTable.DEPLOYMENT_LOG.BUILD_VERSION

    override fun field3(): Field<String?> = DeploymentLogTable.DEPLOYMENT_LOG.SYSTEM_ZONE_ID

    override fun field4(): Field<Instant?> = DeploymentLogTable.DEPLOYMENT_LOG.STARTUP_DATE

    override fun field5(): Field<Instant?> = DeploymentLogTable.DEPLOYMENT_LOG.SHUTDOWN_DATE

    override fun component1(): UUID = id

    override fun component2(): String = buildVersion

    override fun component3(): String = systemZoneId

    override fun component4(): Instant = startupDate

    override fun component5(): Instant? = shutdownDate

    override fun value1(): UUID = id

    override fun value2(): String = buildVersion

    override fun value3(): String = systemZoneId

    override fun value4(): Instant = startupDate

    override fun value5(): Instant? = shutdownDate

    override fun value1(value: UUID?): DeploymentLogRecord {
        set(0, value)
        return this
    }

    override fun value2(value: String?): DeploymentLogRecord {
        set(1, value)
        return this
    }

    override fun value3(value: String?): DeploymentLogRecord {
        set(2, value)
        return this
    }

    override fun value4(value: Instant?): DeploymentLogRecord {
        set(3, value)
        return this
    }

    override fun value5(value: Instant?): DeploymentLogRecord {
        set(4, value)
        return this
    }

    override fun values(
        value1: UUID?,
        value2: String?,
        value3: String?,
        value4: Instant?,
        value5: Instant?
    ): DeploymentLogRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        return this
    }

    /** Create a detached, initialised DeploymentLogRecord */
    constructor(
        id: UUID,
        buildVersion: String,
        systemZoneId: String,
        startupDate: Instant,
        shutdownDate: Instant? = null
    ) : this() {
        this.id = id
        this.buildVersion = buildVersion
        this.systemZoneId = systemZoneId
        this.startupDate = startupDate
        this.shutdownDate = shutdownDate
        resetChangedOnNotNull()
    }
}
