/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables;


import com.kttswebapptemplate.jooq.generated.Keys;
import com.kttswebapptemplate.jooq.generated.PublicTable;
import com.kttswebapptemplate.jooq.generated.tables.records.CommandLogRecord;
import com.kttswebapptemplate.jooqlib.jooq.converter.TimestampWithTimeZoneToInstantJooqConverter;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function13;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row13;
import org.jooq.Schema;
import org.jooq.SelectField;
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
public class CommandLogTable extends TableImpl<CommandLogRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.command_log</code>
     */
    public static final CommandLogTable COMMAND_LOG = new CommandLogTable();

    /**
     * The class holding records for this type
     */
    @Override
    @Nonnull
    public Class<CommandLogRecord> getRecordType() {
        return CommandLogRecord.class;
    }

    /**
     * The column <code>public.command_log.id</code>.
     */
    public final TableField<CommandLogRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.command_log.user_id</code>.
     */
    public final TableField<CommandLogRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>public.command_log.affected_user_id</code>.
     */
    public final TableField<CommandLogRecord, UUID> AFFECTED_USER_ID = createField(DSL.name("affected_user_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>public.command_log.deployment_log_id</code>.
     */
    public final TableField<CommandLogRecord, UUID> DEPLOYMENT_LOG_ID = createField(DSL.name("deployment_log_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.command_log.command_class</code>.
     */
    public final TableField<CommandLogRecord, String> COMMAND_CLASS = createField(DSL.name("command_class"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.command_log.json_command</code>.
     */
    public final TableField<CommandLogRecord, String> JSON_COMMAND = createField(DSL.name("json_command"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.command_log.ip</code>.
     */
    public final TableField<CommandLogRecord, String> IP = createField(DSL.name("ip"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.command_log.user_session_id</code>.
     */
    public final TableField<CommandLogRecord, UUID> USER_SESSION_ID = createField(DSL.name("user_session_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>public.command_log.ids_log</code>.
     */
    public final TableField<CommandLogRecord, String> IDS_LOG = createField(DSL.name("ids_log"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.command_log.json_result</code>.
     */
    public final TableField<CommandLogRecord, String> JSON_RESULT = createField(DSL.name("json_result"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.command_log.exception_stack_trace</code>.
     */
    public final TableField<CommandLogRecord, String> EXCEPTION_STACK_TRACE = createField(DSL.name("exception_stack_trace"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.command_log.start_date</code>.
     */
    public final TableField<CommandLogRecord, Instant> START_DATE = createField(DSL.name("start_date"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new TimestampWithTimeZoneToInstantJooqConverter());

    /**
     * The column <code>public.command_log.end_date</code>.
     */
    public final TableField<CommandLogRecord, Instant> END_DATE = createField(DSL.name("end_date"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new TimestampWithTimeZoneToInstantJooqConverter());

    private CommandLogTable(Name alias, Table<CommandLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private CommandLogTable(Name alias, Table<CommandLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.command_log</code> table reference
     */
    public CommandLogTable(String alias) {
        this(DSL.name(alias), COMMAND_LOG);
    }

    /**
     * Create an aliased <code>public.command_log</code> table reference
     */
    public CommandLogTable(Name alias) {
        this(alias, COMMAND_LOG);
    }

    /**
     * Create a <code>public.command_log</code> table reference
     */
    public CommandLogTable() {
        this(DSL.name("command_log"), null);
    }

    public <O extends Record> CommandLogTable(Table<O> child, ForeignKey<O, CommandLogRecord> key) {
        super(child, key, COMMAND_LOG);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : PublicTable.PUBLIC;
    }

    @Override
    @Nonnull
    public UniqueKey<CommandLogRecord> getPrimaryKey() {
        return Keys.COMMAND_LOG_PKEY;
    }

    @Override
    @Nonnull
    public List<ForeignKey<CommandLogRecord, ?>> getReferences() {
        return Arrays.asList(Keys.COMMAND_LOG__COMMAND_LOG_DEPLOYMENT_LOG_ID_FKEY);
    }

    private transient DeploymentLogTable _deploymentLog;

    /**
     * Get the implicit join path to the <code>public.deployment_log</code>
     * table.
     */
    public DeploymentLogTable deploymentLog() {
        if (_deploymentLog == null)
            _deploymentLog = new DeploymentLogTable(this, Keys.COMMAND_LOG__COMMAND_LOG_DEPLOYMENT_LOG_ID_FKEY);

        return _deploymentLog;
    }

    @Override
    @Nonnull
    public CommandLogTable as(String alias) {
        return new CommandLogTable(DSL.name(alias), this);
    }

    @Override
    @Nonnull
    public CommandLogTable as(Name alias) {
        return new CommandLogTable(alias, this);
    }

    @Override
    @Nonnull
    public CommandLogTable as(Table<?> alias) {
        return new CommandLogTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public CommandLogTable rename(String name) {
        return new CommandLogTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public CommandLogTable rename(Name name) {
        return new CommandLogTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public CommandLogTable rename(Table<?> name) {
        return new CommandLogTable(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row13 type methods
    // -------------------------------------------------------------------------

    @Override
    @Nonnull
    public Row13<UUID, UUID, UUID, UUID, String, String, String, UUID, String, String, String, Instant, Instant> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function13<? super UUID, ? super UUID, ? super UUID, ? super UUID, ? super String, ? super String, ? super String, ? super UUID, ? super String, ? super String, ? super String, ? super Instant, ? super Instant, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function13<? super UUID, ? super UUID, ? super UUID, ? super UUID, ? super String, ? super String, ? super String, ? super UUID, ? super String, ? super String, ? super String, ? super Instant, ? super Instant, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
