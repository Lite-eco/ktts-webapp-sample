/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables

import com.kttswebapptemplate.database.jooq.converter.TimestampWithTimeZoneToInstantJooqConverter
import com.kttswebapptemplate.jooq.generated.PublicTable
import com.kttswebapptemplate.jooq.generated.keys.MAGIC_LINK_TOKEN_PKEY
import com.kttswebapptemplate.jooq.generated.keys.MAGIC_LINK_TOKEN__MAGIC_LINK_TOKEN_USER_ID_FKEY
import com.kttswebapptemplate.jooq.generated.tables.records.MagicLinkTokenRecord
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
open class MagicLinkTokenTable(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, MagicLinkTokenRecord>?,
    aliased: Table<MagicLinkTokenRecord>?,
    parameters: Array<Field<*>?>?
) :
    TableImpl<MagicLinkTokenRecord>(
        alias,
        PublicTable.PUBLIC,
        child,
        path,
        aliased,
        parameters,
        DSL.comment(""),
        TableOptions.table()) {
    companion object {

        /** The reference instance of <code>public.magic_link_token</code> */
        val MAGIC_LINK_TOKEN: MagicLinkTokenTable = MagicLinkTokenTable()
    }

    /** The class holding records for this type */
    override fun getRecordType(): Class<MagicLinkTokenRecord> = MagicLinkTokenRecord::class.java

    /** The column <code>public.magic_link_token.token</code>. */
    val TOKEN: TableField<MagicLinkTokenRecord, String?> =
        createField(DSL.name("token"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /** The column <code>public.magic_link_token.user_id</code>. */
    val USER_ID: TableField<MagicLinkTokenRecord, UUID?> =
        createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "")

    /** The column <code>public.magic_link_token.validity</code>. */
    val VALIDITY: TableField<MagicLinkTokenRecord, Boolean?> =
        createField(DSL.name("validity"), SQLDataType.BOOLEAN.nullable(false), this, "")

    /** The column <code>public.magic_link_token.creation_date</code>. */
    val CREATION_DATE: TableField<MagicLinkTokenRecord, Instant?> =
        createField(
            DSL.name("creation_date"),
            SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false),
            this,
            "",
            TimestampWithTimeZoneToInstantJooqConverter())

    /** The column <code>public.magic_link_token.last_update</code>. */
    val LAST_UPDATE: TableField<MagicLinkTokenRecord, Instant?> =
        createField(
            DSL.name("last_update"),
            SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false),
            this,
            "",
            TimestampWithTimeZoneToInstantJooqConverter())

    private constructor(
        alias: Name,
        aliased: Table<MagicLinkTokenRecord>?
    ) : this(alias, null, null, aliased, null)
    private constructor(
        alias: Name,
        aliased: Table<MagicLinkTokenRecord>?,
        parameters: Array<Field<*>?>?
    ) : this(alias, null, null, aliased, parameters)

    /** Create an aliased <code>public.magic_link_token</code> table reference */
    constructor(alias: String) : this(DSL.name(alias))

    /** Create an aliased <code>public.magic_link_token</code> table reference */
    constructor(alias: Name) : this(alias, null)

    /** Create a <code>public.magic_link_token</code> table reference */
    constructor() : this(DSL.name("magic_link_token"), null)

    constructor(
        child: Table<out Record>,
        key: ForeignKey<out Record, MagicLinkTokenRecord>
    ) : this(Internal.createPathAlias(child, key), child, key, MAGIC_LINK_TOKEN, null)
    override fun getSchema(): Schema? = if (aliased()) null else PublicTable.PUBLIC
    override fun getPrimaryKey(): UniqueKey<MagicLinkTokenRecord> = MAGIC_LINK_TOKEN_PKEY
    override fun getReferences(): List<ForeignKey<MagicLinkTokenRecord, *>> =
        listOf(MAGIC_LINK_TOKEN__MAGIC_LINK_TOKEN_USER_ID_FKEY)

    private lateinit var _appUser: AppUserTable

    /** Get the implicit join path to the <code>public.app_user</code> table. */
    fun appUser(): AppUserTable {
        if (!this::_appUser.isInitialized)
            _appUser = AppUserTable(this, MAGIC_LINK_TOKEN__MAGIC_LINK_TOKEN_USER_ID_FKEY)

        return _appUser
    }

    val appUser: AppUserTable
        get(): AppUserTable = appUser()
    override fun `as`(alias: String): MagicLinkTokenTable =
        MagicLinkTokenTable(DSL.name(alias), this)
    override fun `as`(alias: Name): MagicLinkTokenTable = MagicLinkTokenTable(alias, this)
    override fun `as`(alias: Table<*>): MagicLinkTokenTable =
        MagicLinkTokenTable(alias.getQualifiedName(), this)

    /** Rename this table */
    override fun rename(name: String): MagicLinkTokenTable =
        MagicLinkTokenTable(DSL.name(name), null)

    /** Rename this table */
    override fun rename(name: Name): MagicLinkTokenTable = MagicLinkTokenTable(name, null)

    /** Rename this table */
    override fun rename(name: Table<*>): MagicLinkTokenTable =
        MagicLinkTokenTable(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row5<String?, UUID?, Boolean?, Instant?, Instant?> =
        super.fieldsRow() as Row5<String?, UUID?, Boolean?, Instant?, Instant?>

    /** Convenience mapping calling {@link SelectField#convertFrom(Function)}. */
    fun <U> mapping(from: (String?, UUID?, Boolean?, Instant?, Instant?) -> U): SelectField<U> =
        convertFrom(Records.mapping(from))

    /** Convenience mapping calling {@link SelectField#convertFrom(Class, Function)}. */
    fun <U> mapping(
        toType: Class<U>,
        from: (String?, UUID?, Boolean?, Instant?, Instant?) -> U
    ): SelectField<U> = convertFrom(toType, Records.mapping(from))
}