/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables

import com.kttswebapptemplate.database.jooq.converter.TimestampWithTimeZoneToInstantJooqConverter
import com.kttswebapptemplate.jooq.generated.PublicTable
import com.kttswebapptemplate.jooq.generated.keys.USER_ACCOUNT_OPERATION_TOKEN_PKEY
import com.kttswebapptemplate.jooq.generated.keys.USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_ID_FKEY
import com.kttswebapptemplate.jooq.generated.keys.USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_MAIL_LOG_ID_FKEY
import com.kttswebapptemplate.jooq.generated.tables.AppUserTable.AppUserPath
import com.kttswebapptemplate.jooq.generated.tables.UserMailLogTable.UserMailLogPath
import com.kttswebapptemplate.jooq.generated.tables.records.UserAccountOperationTokenRecord
import java.time.Instant
import java.util.UUID
import kotlin.collections.Collection
import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
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
open class UserAccountOperationTokenTable(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, UserAccountOperationTokenRecord>?,
    parentPath: InverseForeignKey<out Record, UserAccountOperationTokenRecord>?,
    aliased: Table<UserAccountOperationTokenRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
) :
    TableImpl<UserAccountOperationTokenRecord>(
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

        /** The reference instance of <code>public.user_account_operation_token</code> */
        val USER_ACCOUNT_OPERATION_TOKEN: UserAccountOperationTokenTable =
            UserAccountOperationTokenTable()
    }

    /** The class holding records for this type */
    override fun getRecordType(): Class<UserAccountOperationTokenRecord> =
        UserAccountOperationTokenRecord::class.java

    /** The column <code>public.user_account_operation_token.token</code>. */
    val TOKEN: TableField<UserAccountOperationTokenRecord, String?> =
        createField(DSL.name("token"), SQLDataType.VARCHAR(40).nullable(false), this, "")

    /** The column <code>public.user_account_operation_token.token_type</code>. */
    val TOKEN_TYPE: TableField<UserAccountOperationTokenRecord, String?> =
        createField(DSL.name("token_type"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.user_account_operation_token.user_id</code>. */
    val USER_ID: TableField<UserAccountOperationTokenRecord, UUID?> =
        createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "")

    /** The column <code>public.user_account_operation_token.user_mail_log_id</code>. */
    val USER_MAIL_LOG_ID: TableField<UserAccountOperationTokenRecord, UUID?> =
        createField(DSL.name("user_mail_log_id"), SQLDataType.UUID, this, "")

    /** The column <code>public.user_account_operation_token.creation_date</code>. */
    val CREATION_DATE: TableField<UserAccountOperationTokenRecord, Instant?> =
        createField(
            DSL.name("creation_date"),
            SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false),
            this,
            "",
            TimestampWithTimeZoneToInstantJooqConverter())

    private constructor(
        alias: Name,
        aliased: Table<UserAccountOperationTokenRecord>?
    ) : this(alias, null, null, null, aliased, null, null)

    private constructor(
        alias: Name,
        aliased: Table<UserAccountOperationTokenRecord>?,
        parameters: Array<Field<*>?>?
    ) : this(alias, null, null, null, aliased, parameters, null)

    private constructor(
        alias: Name,
        aliased: Table<UserAccountOperationTokenRecord>?,
        where: Condition
    ) : this(alias, null, null, null, aliased, null, where)

    /** Create an aliased <code>public.user_account_operation_token</code> table reference */
    constructor(alias: String) : this(DSL.name(alias))

    /** Create an aliased <code>public.user_account_operation_token</code> table reference */
    constructor(alias: Name) : this(alias, null)

    /** Create a <code>public.user_account_operation_token</code> table reference */
    constructor() : this(DSL.name("user_account_operation_token"), null)

    constructor(
        path: Table<out Record>,
        childPath: ForeignKey<out Record, UserAccountOperationTokenRecord>?,
        parentPath: InverseForeignKey<out Record, UserAccountOperationTokenRecord>?
    ) : this(
        Internal.createPathAlias(path, childPath, parentPath),
        path,
        childPath,
        parentPath,
        USER_ACCOUNT_OPERATION_TOKEN,
        null,
        null)

    /** A subtype implementing {@link Path} for simplified path-based joins. */
    open class UserAccountOperationTokenPath :
        UserAccountOperationTokenTable, Path<UserAccountOperationTokenRecord> {
        constructor(
            path: Table<out Record>,
            childPath: ForeignKey<out Record, UserAccountOperationTokenRecord>?,
            parentPath: InverseForeignKey<out Record, UserAccountOperationTokenRecord>?
        ) : super(path, childPath, parentPath)

        private constructor(
            alias: Name,
            aliased: Table<UserAccountOperationTokenRecord>
        ) : super(alias, aliased)

        override fun `as`(alias: String): UserAccountOperationTokenPath =
            UserAccountOperationTokenPath(DSL.name(alias), this)

        override fun `as`(alias: Name): UserAccountOperationTokenPath =
            UserAccountOperationTokenPath(alias, this)

        override fun `as`(alias: Table<*>): UserAccountOperationTokenPath =
            UserAccountOperationTokenPath(alias.qualifiedName, this)
    }

    override fun getSchema(): Schema? = if (aliased()) null else PublicTable.PUBLIC

    override fun getPrimaryKey(): UniqueKey<UserAccountOperationTokenRecord> =
        USER_ACCOUNT_OPERATION_TOKEN_PKEY

    override fun getReferences(): List<ForeignKey<UserAccountOperationTokenRecord, *>> =
        listOf(
            USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_ID_FKEY,
            USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_MAIL_LOG_ID_FKEY)

    private lateinit var _appUser: AppUserPath

    /** Get the implicit join path to the <code>public.app_user</code> table. */
    fun appUser(): AppUserPath {
        if (!this::_appUser.isInitialized)
            _appUser =
                AppUserPath(
                    this,
                    USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_ID_FKEY,
                    null)

        return _appUser
    }

    val appUser: AppUserPath
        get(): AppUserPath = appUser()

    private lateinit var _userMailLog: UserMailLogPath

    /** Get the implicit join path to the <code>public.user_mail_log</code> table. */
    fun userMailLog(): UserMailLogPath {
        if (!this::_userMailLog.isInitialized)
            _userMailLog =
                UserMailLogPath(
                    this,
                    USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_MAIL_LOG_ID_FKEY,
                    null)

        return _userMailLog
    }

    val userMailLog: UserMailLogPath
        get(): UserMailLogPath = userMailLog()

    override fun `as`(alias: String): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(DSL.name(alias), this)

    override fun `as`(alias: Name): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(alias, this)

    override fun `as`(alias: Table<*>): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(alias.qualifiedName, this)

    /** Rename this table */
    override fun rename(name: String): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(DSL.name(name), null)

    /** Rename this table */
    override fun rename(name: Name): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(name, null)

    /** Rename this table */
    override fun rename(name: Table<*>): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(name.qualifiedName, null)

    /** Create an inline derived table from this table */
    override fun where(condition: Condition): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(qualifiedName, if (aliased()) this else null, condition)

    /** Create an inline derived table from this table */
    override fun where(conditions: Collection<Condition>): UserAccountOperationTokenTable =
        where(DSL.and(conditions))

    /** Create an inline derived table from this table */
    override fun where(vararg conditions: Condition): UserAccountOperationTokenTable =
        where(DSL.and(*conditions))

    /** Create an inline derived table from this table */
    override fun where(condition: Field<Boolean?>): UserAccountOperationTokenTable =
        where(DSL.condition(condition))

    /** Create an inline derived table from this table */
    @PlainSQL
    override fun where(condition: SQL): UserAccountOperationTokenTable =
        where(DSL.condition(condition))

    /** Create an inline derived table from this table */
    @PlainSQL
    override fun where(@Stringly.SQL condition: String): UserAccountOperationTokenTable =
        where(DSL.condition(condition))

    /** Create an inline derived table from this table */
    @PlainSQL
    override fun where(
        @Stringly.SQL condition: String,
        vararg binds: Any?
    ): UserAccountOperationTokenTable = where(DSL.condition(condition, *binds))

    /** Create an inline derived table from this table */
    @PlainSQL
    override fun where(
        @Stringly.SQL condition: String,
        vararg parts: QueryPart
    ): UserAccountOperationTokenTable = where(DSL.condition(condition, *parts))

    /** Create an inline derived table from this table */
    override fun whereExists(select: Select<*>): UserAccountOperationTokenTable =
        where(DSL.exists(select))

    /** Create an inline derived table from this table */
    override fun whereNotExists(select: Select<*>): UserAccountOperationTokenTable =
        where(DSL.notExists(select))
}
