/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables.records

import com.kttswebapptemplate.jooq.generated.tables.MailingLogTable
import java.time.Instant
import java.util.UUID
import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl

/** This class is generated by jOOQ. */
@Suppress("UNCHECKED_CAST")
open class MailingLogRecord private constructor() :
    UpdatableRecordImpl<MailingLogRecord>(MailingLogTable.MAILING_LOG) {

    open var id: UUID
        set(value): Unit = set(0, value)
        get(): UUID = get(0) as UUID

    open var userId: UUID
        set(value): Unit = set(1, value)
        get(): UUID = get(1) as UUID

    open var senderName: String
        set(value): Unit = set(2, value)
        get(): String = get(2) as String

    open var senderMail: String
        set(value): Unit = set(3, value)
        get(): String = get(3) as String

    open var recipientName: String
        set(value): Unit = set(4, value)
        get(): String = get(4) as String

    open var recipientMail: String
        set(value): Unit = set(5, value)
        get(): String = get(5) as String

    open var subject: String
        set(value): Unit = set(6, value)
        get(): String = get(6) as String

    open var content: String
        set(value): Unit = set(7, value)
        get(): String = get(7) as String

    open var `data`: String
        set(value): Unit = set(8, value)
        get(): String = get(8) as String

    open var logDate: Instant
        set(value): Unit = set(9, value)
        get(): Instant = get(9) as Instant

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    /** Create a detached, initialised MailingLogRecord */
    constructor(
        id: UUID,
        userId: UUID,
        senderName: String,
        senderMail: String,
        recipientName: String,
        recipientMail: String,
        subject: String,
        content: String,
        `data`: String,
        logDate: Instant
    ) : this() {
        this.id = id
        this.userId = userId
        this.senderName = senderName
        this.senderMail = senderMail
        this.recipientName = recipientName
        this.recipientMail = recipientMail
        this.subject = subject
        this.content = content
        this.`data` = `data`
        this.logDate = logDate
        resetChangedOnNotNull()
    }
}
