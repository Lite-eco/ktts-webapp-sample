/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables.records

import com.kttswebapptemplate.jooq.generated.tables.AppUserTable
import java.time.Instant
import java.util.UUID
import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record9
import org.jooq.Row9
import org.jooq.impl.UpdatableRecordImpl

/** This class is generated by jOOQ. */
@Suppress("UNCHECKED_CAST")
open class AppUserRecord private constructor() :
    UpdatableRecordImpl<AppUserRecord>(AppUserTable.APP_USER),
    Record9<UUID?, String?, String?, String?, String?, String?, String?, Instant?, Instant?> {

    open var id: UUID
        set(value): Unit = set(0, value)
        get(): UUID = get(0) as UUID

    open var mail: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var password: String
        set(value): Unit = set(2, value)
        get(): String = get(2) as String

    open var displayName: String
        set(value): Unit = set(3, value)
        get(): String = get(3) as String

    open var language: String
        set(value): Unit = set(4, value)
        get(): String = get(4) as String

    open var status: String
        set(value): Unit = set(5, value)
        get(): String = get(5) as String

    open var role: String
        set(value): Unit = set(6, value)
        get(): String = get(6) as String

    open var signupDate: Instant
        set(value): Unit = set(7, value)
        get(): Instant = get(7) as Instant

    open var lastUpdateDate: Instant
        set(value): Unit = set(8, value)
        get(): Instant = get(8) as Instant

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow():
        Row9<UUID?, String?, String?, String?, String?, String?, String?, Instant?, Instant?> =
        super.fieldsRow()
            as Row9<UUID?, String?, String?, String?, String?, String?, String?, Instant?, Instant?>

    override fun valuesRow():
        Row9<UUID?, String?, String?, String?, String?, String?, String?, Instant?, Instant?> =
        super.valuesRow()
            as Row9<UUID?, String?, String?, String?, String?, String?, String?, Instant?, Instant?>

    override fun field1(): Field<UUID?> = AppUserTable.APP_USER.ID

    override fun field2(): Field<String?> = AppUserTable.APP_USER.MAIL

    override fun field3(): Field<String?> = AppUserTable.APP_USER.PASSWORD

    override fun field4(): Field<String?> = AppUserTable.APP_USER.DISPLAY_NAME

    override fun field5(): Field<String?> = AppUserTable.APP_USER.LANGUAGE

    override fun field6(): Field<String?> = AppUserTable.APP_USER.STATUS

    override fun field7(): Field<String?> = AppUserTable.APP_USER.ROLE

    override fun field8(): Field<Instant?> = AppUserTable.APP_USER.SIGNUP_DATE

    override fun field9(): Field<Instant?> = AppUserTable.APP_USER.LAST_UPDATE_DATE

    override fun component1(): UUID = id

    override fun component2(): String = mail

    override fun component3(): String = password

    override fun component4(): String = displayName

    override fun component5(): String = language

    override fun component6(): String = status

    override fun component7(): String = role

    override fun component8(): Instant = signupDate

    override fun component9(): Instant = lastUpdateDate

    override fun value1(): UUID = id

    override fun value2(): String = mail

    override fun value3(): String = password

    override fun value4(): String = displayName

    override fun value5(): String = language

    override fun value6(): String = status

    override fun value7(): String = role

    override fun value8(): Instant = signupDate

    override fun value9(): Instant = lastUpdateDate

    override fun value1(value: UUID?): AppUserRecord {
        set(0, value)
        return this
    }

    override fun value2(value: String?): AppUserRecord {
        set(1, value)
        return this
    }

    override fun value3(value: String?): AppUserRecord {
        set(2, value)
        return this
    }

    override fun value4(value: String?): AppUserRecord {
        set(3, value)
        return this
    }

    override fun value5(value: String?): AppUserRecord {
        set(4, value)
        return this
    }

    override fun value6(value: String?): AppUserRecord {
        set(5, value)
        return this
    }

    override fun value7(value: String?): AppUserRecord {
        set(6, value)
        return this
    }

    override fun value8(value: Instant?): AppUserRecord {
        set(7, value)
        return this
    }

    override fun value9(value: Instant?): AppUserRecord {
        set(8, value)
        return this
    }

    override fun values(
        value1: UUID?,
        value2: String?,
        value3: String?,
        value4: String?,
        value5: String?,
        value6: String?,
        value7: String?,
        value8: Instant?,
        value9: Instant?
    ): AppUserRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        this.value6(value6)
        this.value7(value7)
        this.value8(value8)
        this.value9(value9)
        return this
    }

    /** Create a detached, initialised AppUserRecord */
    constructor(
        id: UUID,
        mail: String,
        password: String,
        displayName: String,
        language: String,
        status: String,
        role: String,
        signupDate: Instant,
        lastUpdateDate: Instant
    ) : this() {
        this.id = id
        this.mail = mail
        this.password = password
        this.displayName = displayName
        this.language = language
        this.status = status
        this.role = role
        this.signupDate = signupDate
        this.lastUpdateDate = lastUpdateDate
        resetChangedOnNotNull()
    }
}
