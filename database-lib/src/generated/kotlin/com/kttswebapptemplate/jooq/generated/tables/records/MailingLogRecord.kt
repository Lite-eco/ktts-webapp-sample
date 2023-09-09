/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables.records

import com.kttswebapptemplate.jooq.generated.tables.MailingLogTable
import java.time.Instant
import java.util.UUID
import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record10
import org.jooq.Row10
import org.jooq.impl.UpdatableRecordImpl

/** This class is generated by jOOQ. */
@Suppress("UNCHECKED_CAST")
open class MailingLogRecord private constructor() :
    UpdatableRecordImpl<MailingLogRecord>(MailingLogTable.MAILING_LOG),
    Record10<
        UUID?, UUID?, String?, String?, String?, String?, String?, String?, String?, Instant?> {

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

    open var date: Instant
        set(value): Unit = set(9, value)
        get(): Instant = get(9) as Instant

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record10 type implementation
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
    override fun valuesRow():
        Row10<
            UUID?, UUID?, String?, String?, String?, String?, String?, String?, String?, Instant?> =
        super.valuesRow()
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
    override fun field1(): Field<UUID?> = MailingLogTable.MAILING_LOG.ID
    override fun field2(): Field<UUID?> = MailingLogTable.MAILING_LOG.USER_ID
    override fun field3(): Field<String?> = MailingLogTable.MAILING_LOG.SENDER_NAME
    override fun field4(): Field<String?> = MailingLogTable.MAILING_LOG.SENDER_MAIL
    override fun field5(): Field<String?> = MailingLogTable.MAILING_LOG.RECIPIENT_NAME
    override fun field6(): Field<String?> = MailingLogTable.MAILING_LOG.RECIPIENT_MAIL
    override fun field7(): Field<String?> = MailingLogTable.MAILING_LOG.SUBJECT
    override fun field8(): Field<String?> = MailingLogTable.MAILING_LOG.CONTENT
    override fun field9(): Field<String?> = MailingLogTable.MAILING_LOG.DATA
    override fun field10(): Field<Instant?> = MailingLogTable.MAILING_LOG.DATE
    override fun component1(): UUID = id
    override fun component2(): UUID = userId
    override fun component3(): String = senderName
    override fun component4(): String = senderMail
    override fun component5(): String = recipientName
    override fun component6(): String = recipientMail
    override fun component7(): String = subject
    override fun component8(): String = content
    override fun component9(): String = `data`
    override fun component10(): Instant = date
    override fun value1(): UUID = id
    override fun value2(): UUID = userId
    override fun value3(): String = senderName
    override fun value4(): String = senderMail
    override fun value5(): String = recipientName
    override fun value6(): String = recipientMail
    override fun value7(): String = subject
    override fun value8(): String = content
    override fun value9(): String = `data`
    override fun value10(): Instant = date

    override fun value1(value: UUID?): MailingLogRecord {
        set(0, value)
        return this
    }

    override fun value2(value: UUID?): MailingLogRecord {
        set(1, value)
        return this
    }

    override fun value3(value: String?): MailingLogRecord {
        set(2, value)
        return this
    }

    override fun value4(value: String?): MailingLogRecord {
        set(3, value)
        return this
    }

    override fun value5(value: String?): MailingLogRecord {
        set(4, value)
        return this
    }

    override fun value6(value: String?): MailingLogRecord {
        set(5, value)
        return this
    }

    override fun value7(value: String?): MailingLogRecord {
        set(6, value)
        return this
    }

    override fun value8(value: String?): MailingLogRecord {
        set(7, value)
        return this
    }

    override fun value9(value: String?): MailingLogRecord {
        set(8, value)
        return this
    }

    override fun value10(value: Instant?): MailingLogRecord {
        set(9, value)
        return this
    }

    override fun values(
        value1: UUID?,
        value2: UUID?,
        value3: String?,
        value4: String?,
        value5: String?,
        value6: String?,
        value7: String?,
        value8: String?,
        value9: String?,
        value10: Instant?
    ): MailingLogRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        this.value6(value6)
        this.value7(value7)
        this.value8(value8)
        this.value9(value9)
        this.value10(value10)
        return this
    }

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
        date: Instant
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
        this.date = date
        resetChangedOnNotNull()
    }
}
