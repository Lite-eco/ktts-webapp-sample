/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables

import com.kttswebapptemplate.database.jooq.converter.TimestampWithTimeZoneToInstantJooqConverter
import com.kttswebapptemplate.jooq.generated.PublicTable
import com.kttswebapptemplate.jooq.generated.indexes.USER_SESSION_LOG_USER_ID_IDX
import com.kttswebapptemplate.jooq.generated.keys.USER_SESSION_LOG_PKEY
import com.kttswebapptemplate.jooq.generated.keys.USER_SESSION_LOG__USER_SESSION_LOG_USER_ID_FKEY
import com.kttswebapptemplate.jooq.generated.tables.records.UserSessionLogRecord
import java.time.Instant
import java.util.UUID
import kotlin.collections.List
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row6
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
open class UserSessionLogTable(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, UserSessionLogRecord>?,
    aliased: Table<UserSessionLogRecord>?,
    parameters: Array<Field<*>?>?
) :
    TableImpl<UserSessionLogRecord>(
        alias,
        PublicTable.PUBLIC,
        child,
        path,
        aliased,
        parameters,
        DSL.comment(""),
        TableOptions.table()) {
    companion object {

        /** The reference instance of <code>public.user_session_log</code> */
        val USER_SESSION_LOG: UserSessionLogTable = UserSessionLogTable()
    }

    /** The class holding records for this type */
    override fun getRecordType(): Class<UserSessionLogRecord> = UserSessionLogRecord::class.java

    /** The column <code>public.user_session_log.id</code>. */
    val ID: TableField<UserSessionLogRecord, UUID?> =
        createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /** The column <code>public.user_session_log.spring_session_id</code>. */
    val SPRING_SESSION_ID: TableField<UserSessionLogRecord, String?> =
        createField(
            DSL.name("spring_session_id"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.user_session_log.user_id</code>. */
    val USER_ID: TableField<UserSessionLogRecord, UUID?> =
        createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "")

    /** The column <code>public.user_session_log.deployment_log_id</code>. */
    val DEPLOYMENT_LOG_ID: TableField<UserSessionLogRecord, UUID?> =
        createField(DSL.name("deployment_log_id"), SQLDataType.UUID.nullable(false), this, "")

    /** The column <code>public.user_session_log.creation_date</code>. */
    val CREATION_DATE: TableField<UserSessionLogRecord, Instant?> =
        createField(
            DSL.name("creation_date"),
            SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false),
            this,
            "",
            TimestampWithTimeZoneToInstantJooqConverter())

    /** The column <code>public.user_session_log.ip</code>. */
    val IP: TableField<UserSessionLogRecord, String?> =
        createField(DSL.name("ip"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(
        alias: Name,
        aliased: Table<UserSessionLogRecord>?
    ) : this(alias, null, null, aliased, null)
    private constructor(
        alias: Name,
        aliased: Table<UserSessionLogRecord>?,
        parameters: Array<Field<*>?>?
    ) : this(alias, null, null, aliased, parameters)

    /** Create an aliased <code>public.user_session_log</code> table reference */
    constructor(alias: String) : this(DSL.name(alias))

    /** Create an aliased <code>public.user_session_log</code> table reference */
    constructor(alias: Name) : this(alias, null)

    /** Create a <code>public.user_session_log</code> table reference */
    constructor() : this(DSL.name("user_session_log"), null)

    constructor(
        child: Table<out Record>,
        key: ForeignKey<out Record, UserSessionLogRecord>
    ) : this(Internal.createPathAlias(child, key), child, key, USER_SESSION_LOG, null)
    override fun getSchema(): Schema? = if (aliased()) null else PublicTable.PUBLIC
    override fun getIndexes(): List<Index> = listOf(USER_SESSION_LOG_USER_ID_IDX)
    override fun getPrimaryKey(): UniqueKey<UserSessionLogRecord> = USER_SESSION_LOG_PKEY
    override fun getReferences(): List<ForeignKey<UserSessionLogRecord, *>> =
        listOf(USER_SESSION_LOG__USER_SESSION_LOG_USER_ID_FKEY)

    private lateinit var _appUser: AppUserTable

    /** Get the implicit join path to the <code>public.app_user</code> table. */
    fun appUser(): AppUserTable {
        if (!this::_appUser.isInitialized)
            _appUser = AppUserTable(this, USER_SESSION_LOG__USER_SESSION_LOG_USER_ID_FKEY)

        return _appUser
    }

    val appUser: AppUserTable
        get(): AppUserTable = appUser()
    override fun `as`(alias: String): UserSessionLogTable =
        UserSessionLogTable(DSL.name(alias), this)
    override fun `as`(alias: Name): UserSessionLogTable = UserSessionLogTable(alias, this)
    override fun `as`(alias: Table<*>): UserSessionLogTable =
        UserSessionLogTable(alias.getQualifiedName(), this)

    /** Rename this table */
    override fun rename(name: String): UserSessionLogTable =
        UserSessionLogTable(DSL.name(name), null)

    /** Rename this table */
    override fun rename(name: Name): UserSessionLogTable = UserSessionLogTable(name, null)

    /** Rename this table */
    override fun rename(name: Table<*>): UserSessionLogTable =
        UserSessionLogTable(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row6<UUID?, String?, UUID?, UUID?, Instant?, String?> =
        super.fieldsRow() as Row6<UUID?, String?, UUID?, UUID?, Instant?, String?>

    /** Convenience mapping calling {@link SelectField#convertFrom(Function)}. */
    fun <U> mapping(from: (UUID?, String?, UUID?, UUID?, Instant?, String?) -> U): SelectField<U> =
        convertFrom(Records.mapping(from))

    /** Convenience mapping calling {@link SelectField#convertFrom(Class, Function)}. */
    fun <U> mapping(
        toType: Class<U>,
        from: (UUID?, String?, UUID?, UUID?, Instant?, String?) -> U
    ): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
