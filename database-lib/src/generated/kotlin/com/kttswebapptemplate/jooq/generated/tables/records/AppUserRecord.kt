/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables.records

import com.kttswebapptemplate.jooq.generated.tables.AppUserTable
import java.time.Instant
import java.util.UUID
import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl

/** This class is generated by jOOQ. */
@Suppress("UNCHECKED_CAST")
open class AppUserRecord private constructor() :
    UpdatableRecordImpl<AppUserRecord>(AppUserTable.APP_USER) {

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
