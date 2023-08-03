/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables.records

import com.kttswebapptemplate.jooq.generated.tables.MagicLinkTokenTable
import java.time.Instant
import java.util.UUID
import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record5
import org.jooq.Row5
import org.jooq.impl.UpdatableRecordImpl

/** This class is generated by jOOQ. */
@Suppress("UNCHECKED_CAST")
open class MagicLinkTokenRecord private constructor() :
    UpdatableRecordImpl<MagicLinkTokenRecord>(MagicLinkTokenTable.MAGIC_LINK_TOKEN),
    Record5<String?, UUID?, Boolean?, Instant?, Instant?> {

    open var token: String
        set(value): Unit = set(0, value)
        get(): String = get(0) as String

    open var userId: UUID
        set(value): Unit = set(1, value)
        get(): UUID = get(1) as UUID

    open var validity: Boolean
        set(value): Unit = set(2, value)
        get(): Boolean = get(2) as Boolean

    open var creationDate: Instant
        set(value): Unit = set(3, value)
        get(): Instant = get(3) as Instant

    open var lastUpdate: Instant
        set(value): Unit = set(4, value)
        get(): Instant = get(4) as Instant

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<String?> = super.key() as Record1<String?>

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row5<String?, UUID?, Boolean?, Instant?, Instant?> =
        super.fieldsRow() as Row5<String?, UUID?, Boolean?, Instant?, Instant?>
    override fun valuesRow(): Row5<String?, UUID?, Boolean?, Instant?, Instant?> =
        super.valuesRow() as Row5<String?, UUID?, Boolean?, Instant?, Instant?>
    override fun field1(): Field<String?> = MagicLinkTokenTable.MAGIC_LINK_TOKEN.TOKEN
    override fun field2(): Field<UUID?> = MagicLinkTokenTable.MAGIC_LINK_TOKEN.USER_ID
    override fun field3(): Field<Boolean?> = MagicLinkTokenTable.MAGIC_LINK_TOKEN.VALIDITY
    override fun field4(): Field<Instant?> = MagicLinkTokenTable.MAGIC_LINK_TOKEN.CREATION_DATE
    override fun field5(): Field<Instant?> = MagicLinkTokenTable.MAGIC_LINK_TOKEN.LAST_UPDATE
    override fun component1(): String = token
    override fun component2(): UUID = userId
    override fun component3(): Boolean = validity
    override fun component4(): Instant = creationDate
    override fun component5(): Instant = lastUpdate
    override fun value1(): String = token
    override fun value2(): UUID = userId
    override fun value3(): Boolean = validity
    override fun value4(): Instant = creationDate
    override fun value5(): Instant = lastUpdate

    override fun value1(value: String?): MagicLinkTokenRecord {
        set(0, value)
        return this
    }

    override fun value2(value: UUID?): MagicLinkTokenRecord {
        set(1, value)
        return this
    }

    override fun value3(value: Boolean?): MagicLinkTokenRecord {
        set(2, value)
        return this
    }

    override fun value4(value: Instant?): MagicLinkTokenRecord {
        set(3, value)
        return this
    }

    override fun value5(value: Instant?): MagicLinkTokenRecord {
        set(4, value)
        return this
    }

    override fun values(
        value1: String?,
        value2: UUID?,
        value3: Boolean?,
        value4: Instant?,
        value5: Instant?
    ): MagicLinkTokenRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        return this
    }

    /** Create a detached, initialised MagicLinkTokenRecord */
    constructor(
        token: String,
        userId: UUID,
        validity: Boolean,
        creationDate: Instant,
        lastUpdate: Instant
    ) : this() {
        this.token = token
        this.userId = userId
        this.validity = validity
        this.creationDate = creationDate
        this.lastUpdate = lastUpdate
        resetChangedOnNotNull()
    }
}
