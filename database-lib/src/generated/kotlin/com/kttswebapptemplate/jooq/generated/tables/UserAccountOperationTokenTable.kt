/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables

import com.kttswebapptemplate.database.jooq.converter.TimestampWithTimeZoneToInstantJooqConverter
import com.kttswebapptemplate.jooq.generated.PublicTable
import com.kttswebapptemplate.jooq.generated.keys.USER_ACCOUNT_OPERATION_TOKEN_PKEY
import com.kttswebapptemplate.jooq.generated.keys.USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_ID_FKEY
import com.kttswebapptemplate.jooq.generated.keys.USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_MAIL_LOG_ID_FKEY
import com.kttswebapptemplate.jooq.generated.tables.records.UserAccountOperationTokenRecord
import java.time.Instant
import java.util.UUID
import kotlin.collections.List
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
open class UserAccountOperationTokenTable(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, UserAccountOperationTokenRecord>?,
    aliased: Table<UserAccountOperationTokenRecord>?,
    parameters: Array<Field<*>?>?
) :
    TableImpl<UserAccountOperationTokenRecord>(
        alias,
        PublicTable.PUBLIC,
        child,
        path,
        aliased,
        parameters,
        DSL.comment(""),
        TableOptions.table()) {
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
    ) : this(alias, null, null, aliased, null)
    private constructor(
        alias: Name,
        aliased: Table<UserAccountOperationTokenRecord>?,
        parameters: Array<Field<*>?>?
    ) : this(alias, null, null, aliased, parameters)

    /** Create an aliased <code>public.user_account_operation_token</code> table reference */
    constructor(alias: String) : this(DSL.name(alias))

    /** Create an aliased <code>public.user_account_operation_token</code> table reference */
    constructor(alias: Name) : this(alias, null)

    /** Create a <code>public.user_account_operation_token</code> table reference */
    constructor() : this(DSL.name("user_account_operation_token"), null)

    constructor(
        child: Table<out Record>,
        key: ForeignKey<out Record, UserAccountOperationTokenRecord>
    ) : this(Internal.createPathAlias(child, key), child, key, USER_ACCOUNT_OPERATION_TOKEN, null)
    override fun getSchema(): Schema? = if (aliased()) null else PublicTable.PUBLIC
    override fun getPrimaryKey(): UniqueKey<UserAccountOperationTokenRecord> =
        USER_ACCOUNT_OPERATION_TOKEN_PKEY
    override fun getReferences(): List<ForeignKey<UserAccountOperationTokenRecord, *>> =
        listOf(
            USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_ID_FKEY,
            USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_MAIL_LOG_ID_FKEY)

    private lateinit var _appUser: AppUserTable
    private lateinit var _userMailLog: UserMailLogTable

    /** Get the implicit join path to the <code>public.app_user</code> table. */
    fun appUser(): AppUserTable {
        if (!this::_appUser.isInitialized)
            _appUser =
                AppUserTable(
                    this, USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_ID_FKEY)

        return _appUser
    }

    val appUser: AppUserTable
        get(): AppUserTable = appUser()

    /** Get the implicit join path to the <code>public.user_mail_log</code> table. */
    fun userMailLog(): UserMailLogTable {
        if (!this::_userMailLog.isInitialized)
            _userMailLog =
                UserMailLogTable(
                    this,
                    USER_ACCOUNT_OPERATION_TOKEN__USER_ACCOUNT_OPERATION_TOKEN_USER_MAIL_LOG_ID_FKEY)

        return _userMailLog
    }

    val userMailLog: UserMailLogTable
        get(): UserMailLogTable = userMailLog()
    override fun `as`(alias: String): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(DSL.name(alias), this)
    override fun `as`(alias: Name): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(alias, this)
    override fun `as`(alias: Table<*>): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(alias.getQualifiedName(), this)

    /** Rename this table */
    override fun rename(name: String): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(DSL.name(name), null)

    /** Rename this table */
    override fun rename(name: Name): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(name, null)

    /** Rename this table */
    override fun rename(name: Table<*>): UserAccountOperationTokenTable =
        UserAccountOperationTokenTable(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row5<String?, String?, UUID?, UUID?, Instant?> =
        super.fieldsRow() as Row5<String?, String?, UUID?, UUID?, Instant?>

    /** Convenience mapping calling {@link SelectField#convertFrom(Function)}. */
    fun <U> mapping(from: (String?, String?, UUID?, UUID?, Instant?) -> U): SelectField<U> =
        convertFrom(Records.mapping(from))

    /** Convenience mapping calling {@link SelectField#convertFrom(Class, Function)}. */
    fun <U> mapping(
        toType: Class<U>,
        from: (String?, String?, UUID?, UUID?, Instant?) -> U
    ): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
