/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables

import com.kttswebapptemplate.database.jooq.converter.TimestampWithTimeZoneToInstantJooqConverter
import com.kttswebapptemplate.jooq.generated.PublicTable
import com.kttswebapptemplate.jooq.generated.indexes.USER_SESSION_LOG_USER_ID_IDX
import com.kttswebapptemplate.jooq.generated.keys.USER_SESSION_LOG_PKEY
import com.kttswebapptemplate.jooq.generated.keys.USER_SESSION_LOG__USER_SESSION_LOG_USER_ID_FKEY
import com.kttswebapptemplate.jooq.generated.tables.AppUserTable.AppUserPath
import com.kttswebapptemplate.jooq.generated.tables.records.UserSessionLogRecord
import java.time.Instant
import java.util.UUID
import kotlin.collections.Collection
import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.Path
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
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
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, UserSessionLogRecord>?,
    parentPath: InverseForeignKey<out Record, UserSessionLogRecord>?,
    aliased: Table<UserSessionLogRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
) :
    TableImpl<UserSessionLogRecord>(
        alias,
        PublicTable.PUBLIC,
        path,
        childPath,
        parentPath,
        aliased,
        parameters,
        DSL.comment(""),
        TableOptions.table(),
        where,
    ) {
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
    ) : this(alias, null, null, null, aliased, null, null)

    private constructor(
        alias: Name,
        aliased: Table<UserSessionLogRecord>?,
        parameters: Array<Field<*>?>?
    ) : this(alias, null, null, null, aliased, parameters, null)

    private constructor(
        alias: Name,
        aliased: Table<UserSessionLogRecord>?,
        where: Condition
    ) : this(alias, null, null, null, aliased, null, where)

    /** Create an aliased <code>public.user_session_log</code> table reference */
    constructor(alias: String) : this(DSL.name(alias))

    /** Create an aliased <code>public.user_session_log</code> table reference */
    constructor(alias: Name) : this(alias, null)

    /** Create a <code>public.user_session_log</code> table reference */
    constructor() : this(DSL.name("user_session_log"), null)

    constructor(
        path: Table<out Record>,
        childPath: ForeignKey<out Record, UserSessionLogRecord>?,
        parentPath: InverseForeignKey<out Record, UserSessionLogRecord>?
    ) : this(
        Internal.createPathAlias(path, childPath, parentPath),
        path,
        childPath,
        parentPath,
        USER_SESSION_LOG,
        null,
        null)

    /** A subtype implementing {@link Path} for simplified path-based joins. */
    open class UserSessionLogPath : UserSessionLogTable, Path<UserSessionLogRecord> {
        constructor(
            path: Table<out Record>,
            childPath: ForeignKey<out Record, UserSessionLogRecord>?,
            parentPath: InverseForeignKey<out Record, UserSessionLogRecord>?
        ) : super(path, childPath, parentPath)

        private constructor(
            alias: Name,
            aliased: Table<UserSessionLogRecord>
        ) : super(alias, aliased)

        override fun `as`(alias: String): UserSessionLogPath =
            UserSessionLogPath(DSL.name(alias), this)

        override fun `as`(alias: Name): UserSessionLogPath = UserSessionLogPath(alias, this)

        override fun `as`(alias: Table<*>): UserSessionLogPath =
            UserSessionLogPath(alias.qualifiedName, this)
    }

    override fun getSchema(): Schema? = if (aliased()) null else PublicTable.PUBLIC

    override fun getIndexes(): List<Index> = listOf(USER_SESSION_LOG_USER_ID_IDX)

    override fun getPrimaryKey(): UniqueKey<UserSessionLogRecord> = USER_SESSION_LOG_PKEY

    override fun getReferences(): List<ForeignKey<UserSessionLogRecord, *>> =
        listOf(USER_SESSION_LOG__USER_SESSION_LOG_USER_ID_FKEY)

    private lateinit var _appUser: AppUserPath

    /** Get the implicit join path to the <code>public.app_user</code> table. */
    fun appUser(): AppUserPath {
        if (!this::_appUser.isInitialized)
            _appUser = AppUserPath(this, USER_SESSION_LOG__USER_SESSION_LOG_USER_ID_FKEY, null)

        return _appUser
    }

    val appUser: AppUserPath
        get(): AppUserPath = appUser()

    override fun `as`(alias: String): UserSessionLogTable =
        UserSessionLogTable(DSL.name(alias), this)

    override fun `as`(alias: Name): UserSessionLogTable = UserSessionLogTable(alias, this)

    override fun `as`(alias: Table<*>): UserSessionLogTable =
        UserSessionLogTable(alias.qualifiedName, this)

    /** Rename this table */
    override fun rename(name: String): UserSessionLogTable =
        UserSessionLogTable(DSL.name(name), null)

    /** Rename this table */
    override fun rename(name: Name): UserSessionLogTable = UserSessionLogTable(name, null)

    /** Rename this table */
    override fun rename(name: Table<*>): UserSessionLogTable =
        UserSessionLogTable(name.qualifiedName, null)

    /** Create an inline derived table from this table */
    override fun where(condition: Condition): UserSessionLogTable =
        UserSessionLogTable(qualifiedName, if (aliased()) this else null, condition)

    /** Create an inline derived table from this table */
    override fun where(conditions: Collection<Condition>): UserSessionLogTable =
        where(DSL.and(conditions))

    /** Create an inline derived table from this table */
    override fun where(vararg conditions: Condition): UserSessionLogTable =
        where(DSL.and(*conditions))

    /** Create an inline derived table from this table */
    override fun where(condition: Field<Boolean?>): UserSessionLogTable =
        where(DSL.condition(condition))

    /** Create an inline derived table from this table */
    @PlainSQL
    override fun where(condition: SQL): UserSessionLogTable = where(DSL.condition(condition))

    /** Create an inline derived table from this table */
    @PlainSQL
    override fun where(@Stringly.SQL condition: String): UserSessionLogTable =
        where(DSL.condition(condition))

    /** Create an inline derived table from this table */
    @PlainSQL
    override fun where(@Stringly.SQL condition: String, vararg binds: Any?): UserSessionLogTable =
        where(DSL.condition(condition, *binds))

    /** Create an inline derived table from this table */
    @PlainSQL
    override fun where(
        @Stringly.SQL condition: String,
        vararg parts: QueryPart
    ): UserSessionLogTable = where(DSL.condition(condition, *parts))

    /** Create an inline derived table from this table */
    override fun whereExists(select: Select<*>): UserSessionLogTable = where(DSL.exists(select))

    /** Create an inline derived table from this table */
    override fun whereNotExists(select: Select<*>): UserSessionLogTable =
        where(DSL.notExists(select))
}
