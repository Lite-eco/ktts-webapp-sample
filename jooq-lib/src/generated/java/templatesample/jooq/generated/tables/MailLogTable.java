/*
 * This file is generated by jOOQ.
 */
package templatesample.jooq.generated.tables;


import templatesample.jooq.generated.Indexes;
import templatesample.jooq.generated.Keys;
import templatesample.jooq.generated.PublicTable;
import templatesample.jooq.generated.tables.records.MailLogRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import jooqutils.jooq.TimestampWithTimeZoneToInstantConverter;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row11;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MailLogTable extends TableImpl<MailLogRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.mail_log</code>
     */
    public static final MailLogTable MAIL_LOG = new MailLogTable();

    /**
     * The class holding records for this type
     */
    @Override
    @Nonnull
    public Class<MailLogRecord> getRecordType() {
        return MailLogRecord.class;
    }

    /**
     * The column <code>public.mail_log.id</code>.
     */
    public final TableField<MailLogRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.mail_log.application</code>.
     */
    public final TableField<MailLogRecord, String> APPLICATION = createField(DSL.name("application"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.mail_log.deployment_log_id</code>.
     */
    public final TableField<MailLogRecord, UUID> DEPLOYMENT_LOG_ID = createField(DSL.name("deployment_log_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.mail_log.recipient_type</code>.
     */
    public final TableField<MailLogRecord, String> RECIPIENT_TYPE = createField(DSL.name("recipient_type"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.mail_log.user_id</code>.
     */
    public final TableField<MailLogRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.mail_log.reference</code>.
     */
    public final TableField<MailLogRecord, String> REFERENCE = createField(DSL.name("reference"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.mail_log.recipient_mail</code>.
     */
    public final TableField<MailLogRecord, String> RECIPIENT_MAIL = createField(DSL.name("recipient_mail"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.mail_log.data</code>.
     */
    public final TableField<MailLogRecord, String> DATA = createField(DSL.name("data"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.mail_log.subject</code>.
     */
    public final TableField<MailLogRecord, String> SUBJECT = createField(DSL.name("subject"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.mail_log.content</code>.
     */
    public final TableField<MailLogRecord, String> CONTENT = createField(DSL.name("content"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.mail_log.date</code>.
     */
    public final TableField<MailLogRecord, Instant> DATE = createField(DSL.name("date"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new TimestampWithTimeZoneToInstantConverter());

    private MailLogTable(Name alias, Table<MailLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private MailLogTable(Name alias, Table<MailLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.mail_log</code> table reference
     */
    public MailLogTable(String alias) {
        this(DSL.name(alias), MAIL_LOG);
    }

    /**
     * Create an aliased <code>public.mail_log</code> table reference
     */
    public MailLogTable(Name alias) {
        this(alias, MAIL_LOG);
    }

    /**
     * Create a <code>public.mail_log</code> table reference
     */
    public MailLogTable() {
        this(DSL.name("mail_log"), null);
    }

    public <O extends Record> MailLogTable(Table<O> child, ForeignKey<O, MailLogRecord> key) {
        super(child, key, MAIL_LOG);
    }

    @Override
    @Nonnull
    public Schema getSchema() {
        return PublicTable.PUBLIC;
    }

    @Override
    @Nonnull
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MAIL_LOG_USER_ID_IDX);
    }

    @Override
    @Nonnull
    public UniqueKey<MailLogRecord> getPrimaryKey() {
        return Keys.MAIL_LOG_PKEY;
    }

    @Override
    @Nonnull
    public List<UniqueKey<MailLogRecord>> getKeys() {
        return Arrays.<UniqueKey<MailLogRecord>>asList(Keys.MAIL_LOG_PKEY);
    }

    @Override
    @Nonnull
    public List<ForeignKey<MailLogRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MailLogRecord, ?>>asList(Keys.MAIL_LOG__MAIL_LOG_DEPLOYMENT_LOG_ID_FKEY);
    }

    private transient DeploymentLogTable _deploymentLog;

    public DeploymentLogTable deploymentLog() {
        if (_deploymentLog == null)
            _deploymentLog = new DeploymentLogTable(this, Keys.MAIL_LOG__MAIL_LOG_DEPLOYMENT_LOG_ID_FKEY);

        return _deploymentLog;
    }

    @Override
    @Nonnull
    public MailLogTable as(String alias) {
        return new MailLogTable(DSL.name(alias), this);
    }

    @Override
    @Nonnull
    public MailLogTable as(Name alias) {
        return new MailLogTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public MailLogTable rename(String name) {
        return new MailLogTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public MailLogTable rename(Name name) {
        return new MailLogTable(name, null);
    }

    // -------------------------------------------------------------------------
    // Row11 type methods
    // -------------------------------------------------------------------------

    @Override
    @Nonnull
    public Row11<UUID, String, UUID, String, UUID, String, String, String, String, String, Instant> fieldsRow() {
        return (Row11) super.fieldsRow();
    }
}
