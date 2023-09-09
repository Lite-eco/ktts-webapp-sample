/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables

import com.kttswebapptemplate.database.jooq.converter.TimestampWithTimeZoneToInstantJooqConverter
import com.kttswebapptemplate.jooq.generated.PublicTable
import com.kttswebapptemplate.jooq.generated.keys.MAILING_LOG_PKEY
import com.kttswebapptemplate.jooq.generated.keys.MAILING_LOG__MAILING_LOG_USER_ID_FKEY
import com.kttswebapptemplate.jooq.generated.tables.records.MailingLogRecord
import java.time.Instant
import java.util.UUID
import kotlin.collections.List
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row10
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
open class MailingLogTable(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, MailingLogRecord>?,
    aliased: Table<MailingLogRecord>?,
    parameters: Array<Field<*>?>?
) :
    TableImpl<MailingLogRecord>(
        alias,
        PublicTable.PUBLIC,
        child,
        path,
        aliased,
        parameters,
        DSL.comment(""),
        TableOptions.table()) {
    companion object {

        /** The reference instance of <code>public.mailing_log</code> */
        val MAILING_LOG: MailingLogTable = MailingLogTable()
    }

    /** The class holding records for this type */
    override fun getRecordType(): Class<MailingLogRecord> = MailingLogRecord::class.java

    /** The column <code>public.mailing_log.id</code>. */
    val ID: TableField<MailingLogRecord, UUID?> =
        createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /** The column <code>public.mailing_log.user_id</code>. */
    val USER_ID: TableField<MailingLogRecord, UUID?> =
        createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "")

    /** The column <code>public.mailing_log.sender_name</code>. */
    val SENDER_NAME: TableField<MailingLogRecord, String?> =
        createField(DSL.name("sender_name"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.mailing_log.sender_mail</code>. */
    val SENDER_MAIL: TableField<MailingLogRecord, String?> =
        createField(DSL.name("sender_mail"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.mailing_log.recipient_name</code>. */
    val RECIPIENT_NAME: TableField<MailingLogRecord, String?> =
        createField(DSL.name("recipient_name"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.mailing_log.recipient_mail</code>. */
    val RECIPIENT_MAIL: TableField<MailingLogRecord, String?> =
        createField(DSL.name("recipient_mail"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.mailing_log.subject</code>. */
    val SUBJECT: TableField<MailingLogRecord, String?> =
        createField(DSL.name("subject"), SQLDataType.CLOB.nullable(false), this, "")

    /** The column <code>public.mailing_log.content</code>. */
    val CONTENT: TableField<MailingLogRecord, String?> =
        createField(DSL.name("content"), SQLDataType.CLOB.nullable(false), this, "")

    /** The column <code>public.mailing_log.data</code>. */
    val DATA: TableField<MailingLogRecord, String?> =
        createField(DSL.name("data"), SQLDataType.CLOB.nullable(false), this, "")

    /** The column <code>public.mailing_log.date</code>. */
    val DATE: TableField<MailingLogRecord, Instant?> =
        createField(
            DSL.name("date"),
            SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false),
            this,
            "",
            TimestampWithTimeZoneToInstantJooqConverter())

    private constructor(
        alias: Name,
        aliased: Table<MailingLogRecord>?
    ) : this(alias, null, null, aliased, null)
    private constructor(
        alias: Name,
        aliased: Table<MailingLogRecord>?,
        parameters: Array<Field<*>?>?
    ) : this(alias, null, null, aliased, parameters)

    /** Create an aliased <code>public.mailing_log</code> table reference */
    constructor(alias: String) : this(DSL.name(alias))

    /** Create an aliased <code>public.mailing_log</code> table reference */
    constructor(alias: Name) : this(alias, null)

    /** Create a <code>public.mailing_log</code> table reference */
    constructor() : this(DSL.name("mailing_log"), null)

    constructor(
        child: Table<out Record>,
        key: ForeignKey<out Record, MailingLogRecord>
    ) : this(Internal.createPathAlias(child, key), child, key, MAILING_LOG, null)
    override fun getSchema(): Schema? = if (aliased()) null else PublicTable.PUBLIC
    override fun getPrimaryKey(): UniqueKey<MailingLogRecord> = MAILING_LOG_PKEY
    override fun getReferences(): List<ForeignKey<MailingLogRecord, *>> =
        listOf(MAILING_LOG__MAILING_LOG_USER_ID_FKEY)

    private lateinit var _appUser: AppUserTable

    /** Get the implicit join path to the <code>public.app_user</code> table. */
    fun appUser(): AppUserTable {
        if (!this::_appUser.isInitialized)
            _appUser = AppUserTable(this, MAILING_LOG__MAILING_LOG_USER_ID_FKEY)

        return _appUser
    }

    val appUser: AppUserTable
        get(): AppUserTable = appUser()
    override fun `as`(alias: String): MailingLogTable = MailingLogTable(DSL.name(alias), this)
    override fun `as`(alias: Name): MailingLogTable = MailingLogTable(alias, this)
    override fun `as`(alias: Table<*>): MailingLogTable =
        MailingLogTable(alias.getQualifiedName(), this)

    /** Rename this table */
    override fun rename(name: String): MailingLogTable = MailingLogTable(DSL.name(name), null)

    /** Rename this table */
    override fun rename(name: Name): MailingLogTable = MailingLogTable(name, null)

    /** Rename this table */
    override fun rename(name: Table<*>): MailingLogTable =
        MailingLogTable(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow():
        Row10<
            UUID?, UUID?, String?, String?, String?, String?, String?, String?, String?, Instant?> =
        super.fieldsRow()
            as
            Row10<
                UUID?,
                UUID?,
                String?,
                String?,
                String?,
                String?,
                String?,
                String?,
                String?,
                Instant?>

    /** Convenience mapping calling {@link SelectField#convertFrom(Function)}. */
    fun <U> mapping(
        from:
            (
                UUID?,
                UUID?,
                String?,
                String?,
                String?,
                String?,
                String?,
                String?,
                String?,
                Instant?) -> U
    ): SelectField<U> = convertFrom(Records.mapping(from))

    /** Convenience mapping calling {@link SelectField#convertFrom(Class, Function)}. */
    fun <U> mapping(
        toType: Class<U>,
        from:
            (
                UUID?,
                UUID?,
                String?,
                String?,
                String?,
                String?,
                String?,
                String?,
                String?,
                Instant?) -> U
    ): SelectField<U> = convertFrom(toType, Records.mapping(from))
}