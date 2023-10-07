/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables

import com.kttswebapptemplate.database.jooq.converter.TimestampWithTimeZoneToInstantJooqConverter
import com.kttswebapptemplate.jooq.generated.PublicTable
import com.kttswebapptemplate.jooq.generated.keys.DEPLOYMENT_LOG_PKEY
import com.kttswebapptemplate.jooq.generated.tables.records.DeploymentLogRecord
import java.time.Instant
import java.util.UUID
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row5
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl

/** This class is generated by jOOQ. */
@Suppress("UNCHECKED_CAST")
open class DeploymentLogTable(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, DeploymentLogRecord>?,
    aliased: Table<DeploymentLogRecord>?,
    parameters: Array<Field<*>?>?
) :
    TableImpl<DeploymentLogRecord>(
        alias,
        PublicTable.PUBLIC,
        child,
        path,
        aliased,
        parameters,
        DSL.comment(""),
        TableOptions.table()) {
    companion object {

        /** The reference instance of <code>public.deployment_log</code> */
        val DEPLOYMENT_LOG: DeploymentLogTable = DeploymentLogTable()
    }

    /** The class holding records for this type */
    override fun getRecordType(): Class<DeploymentLogRecord> = DeploymentLogRecord::class.java

    /** The column <code>public.deployment_log.id</code>. */
    val ID: TableField<DeploymentLogRecord, UUID?> =
        createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /** The column <code>public.deployment_log.build_version</code>. */
    val BUILD_VERSION: TableField<DeploymentLogRecord, String?> =
        createField(DSL.name("build_version"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.deployment_log.system_zone_id</code>. */
    val SYSTEM_ZONE_ID: TableField<DeploymentLogRecord, String?> =
        createField(DSL.name("system_zone_id"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.deployment_log.startup_date</code>. */
    val STARTUP_DATE: TableField<DeploymentLogRecord, Instant?> =
        createField(
            DSL.name("startup_date"),
            SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false),
            this,
            "",
            TimestampWithTimeZoneToInstantJooqConverter())

    /** The column <code>public.deployment_log.shutdown_date</code>. */
    val SHUTDOWN_DATE: TableField<DeploymentLogRecord, Instant?> =
        createField(
            DSL.name("shutdown_date"),
            SQLDataType.TIMESTAMPWITHTIMEZONE(6),
            this,
            "",
            TimestampWithTimeZoneToInstantJooqConverter())

    private constructor(
        alias: Name,
        aliased: Table<DeploymentLogRecord>?
    ) : this(alias, null, null, aliased, null)

    private constructor(
        alias: Name,
        aliased: Table<DeploymentLogRecord>?,
        parameters: Array<Field<*>?>?
    ) : this(alias, null, null, aliased, parameters)

    /** Create an aliased <code>public.deployment_log</code> table reference */
    constructor(alias: String) : this(DSL.name(alias))

    /** Create an aliased <code>public.deployment_log</code> table reference */
    constructor(alias: Name) : this(alias, null)

    /** Create a <code>public.deployment_log</code> table reference */
    constructor() : this(DSL.name("deployment_log"), null)

    constructor(
        child: Table<out Record>,
        key: ForeignKey<out Record, DeploymentLogRecord>
    ) : this(Internal.createPathAlias(child, key), child, key, DEPLOYMENT_LOG, null)

    override fun getSchema(): Schema? = if (aliased()) null else PublicTable.PUBLIC

    override fun getPrimaryKey(): UniqueKey<DeploymentLogRecord> = DEPLOYMENT_LOG_PKEY

    override fun `as`(alias: String): DeploymentLogTable = DeploymentLogTable(DSL.name(alias), this)

    override fun `as`(alias: Name): DeploymentLogTable = DeploymentLogTable(alias, this)

    override fun `as`(alias: Table<*>): DeploymentLogTable =
        DeploymentLogTable(alias.getQualifiedName(), this)

    /** Rename this table */
    override fun rename(name: String): DeploymentLogTable = DeploymentLogTable(DSL.name(name), null)

    /** Rename this table */
    override fun rename(name: Name): DeploymentLogTable = DeploymentLogTable(name, null)

    /** Rename this table */
    override fun rename(name: Table<*>): DeploymentLogTable =
        DeploymentLogTable(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row5<UUID?, String?, String?, Instant?, Instant?> =
        super.fieldsRow() as Row5<UUID?, String?, String?, Instant?, Instant?>

    /** Convenience mapping calling {@link SelectField#convertFrom(Function)}. */
    fun <U> mapping(from: (UUID?, String?, String?, Instant?, Instant?) -> U): SelectField<U> =
        convertFrom(Records.mapping(from))

    /** Convenience mapping calling {@link SelectField#convertFrom(Class, Function)}. */
    fun <U> mapping(
        toType: Class<U>,
        from: (UUID?, String?, String?, Instant?, Instant?) -> U
    ): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
