/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables;


import com.kttswebapptemplate.jooq.generated.Indexes;
import com.kttswebapptemplate.jooq.generated.Keys;
import com.kttswebapptemplate.jooq.generated.PublicTable;
import com.kttswebapptemplate.jooq.generated.tables.records.UserSessionLogRecord;
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
import org.jooq.Function6;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row6;
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
public class UserSessionLogTable extends TableImpl<UserSessionLogRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user_session_log</code>
     */
    public static final UserSessionLogTable USER_SESSION_LOG = new UserSessionLogTable();

    /**
     * The class holding records for this type
     */
    @Override
    @Nonnull
    public Class<UserSessionLogRecord> getRecordType() {
        return UserSessionLogRecord.class;
    }

    /**
     * The column <code>public.user_session_log.id</code>.
     */
    public final TableField<UserSessionLogRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_session_log.spring_session_id</code>.
     */
    public final TableField<UserSessionLogRecord, String> SPRING_SESSION_ID = createField(DSL.name("spring_session_id"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.user_session_log.user_id</code>.
     */
    public final TableField<UserSessionLogRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_session_log.deployment_log_id</code>.
     */
    public final TableField<UserSessionLogRecord, UUID> DEPLOYMENT_LOG_ID = createField(DSL.name("deployment_log_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_session_log.creation_date</code>.
     */
    public final TableField<UserSessionLogRecord, Instant> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new TimestampWithTimeZoneToInstantJooqConverter());

    /**
     * The column <code>public.user_session_log.ip</code>.
     */
    public final TableField<UserSessionLogRecord, String> IP = createField(DSL.name("ip"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    private UserSessionLogTable(Name alias, Table<UserSessionLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserSessionLogTable(Name alias, Table<UserSessionLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.user_session_log</code> table reference
     */
    public UserSessionLogTable(String alias) {
        this(DSL.name(alias), USER_SESSION_LOG);
    }

    /**
     * Create an aliased <code>public.user_session_log</code> table reference
     */
    public UserSessionLogTable(Name alias) {
        this(alias, USER_SESSION_LOG);
    }

    /**
     * Create a <code>public.user_session_log</code> table reference
     */
    public UserSessionLogTable() {
        this(DSL.name("user_session_log"), null);
    }

    public <O extends Record> UserSessionLogTable(Table<O> child, ForeignKey<O, UserSessionLogRecord> key) {
        super(child, key, USER_SESSION_LOG);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : PublicTable.PUBLIC;
    }

    @Override
    @Nonnull
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.USER_SESSION_LOG_USER_ID_IDX);
    }

    @Override
    @Nonnull
    public UniqueKey<UserSessionLogRecord> getPrimaryKey() {
        return Keys.USER_SESSION_LOG_PKEY;
    }

    @Override
    @Nonnull
    public List<ForeignKey<UserSessionLogRecord, ?>> getReferences() {
        return Arrays.asList(Keys.USER_SESSION_LOG__USER_SESSION_LOG_USER_ID_FKEY);
    }

    private transient AppUserTable _appUser;

    /**
     * Get the implicit join path to the <code>public.app_user</code> table.
     */
    public AppUserTable appUser() {
        if (_appUser == null)
            _appUser = new AppUserTable(this, Keys.USER_SESSION_LOG__USER_SESSION_LOG_USER_ID_FKEY);

        return _appUser;
    }

    @Override
    @Nonnull
    public UserSessionLogTable as(String alias) {
        return new UserSessionLogTable(DSL.name(alias), this);
    }

    @Override
    @Nonnull
    public UserSessionLogTable as(Name alias) {
        return new UserSessionLogTable(alias, this);
    }

    @Override
    @Nonnull
    public UserSessionLogTable as(Table<?> alias) {
        return new UserSessionLogTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public UserSessionLogTable rename(String name) {
        return new UserSessionLogTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public UserSessionLogTable rename(Name name) {
        return new UserSessionLogTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public UserSessionLogTable rename(Table<?> name) {
        return new UserSessionLogTable(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    @Nonnull
    public Row6<UUID, String, UUID, UUID, Instant, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function6<? super UUID, ? super String, ? super UUID, ? super UUID, ? super Instant, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super UUID, ? super String, ? super UUID, ? super UUID, ? super Instant, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
