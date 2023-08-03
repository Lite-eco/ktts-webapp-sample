/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.tables;


import com.kttswebapptemplate.jooq.generated.Keys;
import com.kttswebapptemplate.jooq.generated.PublicTable;
import com.kttswebapptemplate.jooq.generated.tables.records.MagicLinkTokenRecord;
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
import org.jooq.Function5;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
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
public class MagicLinkTokenTable extends TableImpl<MagicLinkTokenRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.magic_link_token</code>
     */
    public static final MagicLinkTokenTable MAGIC_LINK_TOKEN = new MagicLinkTokenTable();

    /**
     * The class holding records for this type
     */
    @Override
    @Nonnull
    public Class<MagicLinkTokenRecord> getRecordType() {
        return MagicLinkTokenRecord.class;
    }

    /**
     * The column <code>public.magic_link_token.token</code>.
     */
    public final TableField<MagicLinkTokenRecord, String> TOKEN = createField(DSL.name("token"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.magic_link_token.user_id</code>.
     */
    public final TableField<MagicLinkTokenRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.magic_link_token.validity</code>.
     */
    public final TableField<MagicLinkTokenRecord, Boolean> VALIDITY = createField(DSL.name("validity"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.magic_link_token.creation_date</code>.
     */
    public final TableField<MagicLinkTokenRecord, Instant> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new TimestampWithTimeZoneToInstantJooqConverter());

    /**
     * The column <code>public.magic_link_token.last_update</code>.
     */
    public final TableField<MagicLinkTokenRecord, Instant> LAST_UPDATE = createField(DSL.name("last_update"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new TimestampWithTimeZoneToInstantJooqConverter());

    private MagicLinkTokenTable(Name alias, Table<MagicLinkTokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private MagicLinkTokenTable(Name alias, Table<MagicLinkTokenRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.magic_link_token</code> table reference
     */
    public MagicLinkTokenTable(String alias) {
        this(DSL.name(alias), MAGIC_LINK_TOKEN);
    }

    /**
     * Create an aliased <code>public.magic_link_token</code> table reference
     */
    public MagicLinkTokenTable(Name alias) {
        this(alias, MAGIC_LINK_TOKEN);
    }

    /**
     * Create a <code>public.magic_link_token</code> table reference
     */
    public MagicLinkTokenTable() {
        this(DSL.name("magic_link_token"), null);
    }

    public <O extends Record> MagicLinkTokenTable(Table<O> child, ForeignKey<O, MagicLinkTokenRecord> key) {
        super(child, key, MAGIC_LINK_TOKEN);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : PublicTable.PUBLIC;
    }

    @Override
    @Nonnull
    public UniqueKey<MagicLinkTokenRecord> getPrimaryKey() {
        return Keys.MAGIC_LINK_TOKEN_PKEY;
    }

    @Override
    @Nonnull
    public List<ForeignKey<MagicLinkTokenRecord, ?>> getReferences() {
        return Arrays.asList(Keys.MAGIC_LINK_TOKEN__MAGIC_LINK_TOKEN_USER_ID_FKEY);
    }

    private transient AppUserTable _appUser;

    /**
     * Get the implicit join path to the <code>public.app_user</code> table.
     */
    public AppUserTable appUser() {
        if (_appUser == null)
            _appUser = new AppUserTable(this, Keys.MAGIC_LINK_TOKEN__MAGIC_LINK_TOKEN_USER_ID_FKEY);

        return _appUser;
    }

    @Override
    @Nonnull
    public MagicLinkTokenTable as(String alias) {
        return new MagicLinkTokenTable(DSL.name(alias), this);
    }

    @Override
    @Nonnull
    public MagicLinkTokenTable as(Name alias) {
        return new MagicLinkTokenTable(alias, this);
    }

    @Override
    @Nonnull
    public MagicLinkTokenTable as(Table<?> alias) {
        return new MagicLinkTokenTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public MagicLinkTokenTable rename(String name) {
        return new MagicLinkTokenTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public MagicLinkTokenTable rename(Name name) {
        return new MagicLinkTokenTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public MagicLinkTokenTable rename(Table<?> name) {
        return new MagicLinkTokenTable(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    @Nonnull
    public Row5<String, UUID, Boolean, Instant, Instant> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function5<? super String, ? super UUID, ? super Boolean, ? super Instant, ? super Instant, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super String, ? super UUID, ? super Boolean, ? super Instant, ? super Instant, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
