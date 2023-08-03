/*
 * This file is generated by jOOQ.
 */
package templatesample.jooq.generated.tables;


import templatesample.jooq.generated.Indexes;
import templatesample.jooq.generated.Keys;
import templatesample.jooq.generated.PublicTable;
import templatesample.jooq.generated.tables.records.AppUserRecord;

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
import org.jooq.Row10;
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
public class AppUserTable extends TableImpl<AppUserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.app_user</code>
     */
    public static final AppUserTable APP_USER = new AppUserTable();

    /**
     * The class holding records for this type
     */
    @Override
    @Nonnull
    public Class<AppUserRecord> getRecordType() {
        return AppUserRecord.class;
    }

    /**
     * The column <code>public.app_user.id</code>.
     */
    public final TableField<AppUserRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.app_user.mail</code>.
     */
    public final TableField<AppUserRecord, String> MAIL = createField(DSL.name("mail"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.app_user.password</code>.
     */
    public final TableField<AppUserRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.VARCHAR(60).nullable(false), this, "");

    /**
     * The column <code>public.app_user.username</code>.
     */
    public final TableField<AppUserRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.app_user.display_name</code>.
     */
    public final TableField<AppUserRecord, String> DISPLAY_NAME = createField(DSL.name("display_name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.app_user.language</code>.
     */
    public final TableField<AppUserRecord, String> LANGUAGE = createField(DSL.name("language"), SQLDataType.VARCHAR(2).nullable(false), this, "");

    /**
     * The column <code>public.app_user.roles</code>.
     */
    public final TableField<AppUserRecord, String[]> ROLES = createField(DSL.name("roles"), SQLDataType.VARCHAR(255).getArrayDataType(), this, "");

    /**
     * The column <code>public.app_user.dirty_mail</code>.
     */
    public final TableField<AppUserRecord, String> DIRTY_MAIL = createField(DSL.name("dirty_mail"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.app_user.signup_date</code>.
     */
    public final TableField<AppUserRecord, Instant> SIGNUP_DATE = createField(DSL.name("signup_date"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new TimestampWithTimeZoneToInstantConverter());

    /**
     * The column <code>public.app_user.last_update_date</code>.
     */
    public final TableField<AppUserRecord, Instant> LAST_UPDATE_DATE = createField(DSL.name("last_update_date"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new TimestampWithTimeZoneToInstantConverter());

    private AppUserTable(Name alias, Table<AppUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private AppUserTable(Name alias, Table<AppUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.app_user</code> table reference
     */
    public AppUserTable(String alias) {
        this(DSL.name(alias), APP_USER);
    }

    /**
     * Create an aliased <code>public.app_user</code> table reference
     */
    public AppUserTable(Name alias) {
        this(alias, APP_USER);
    }

    /**
     * Create a <code>public.app_user</code> table reference
     */
    public AppUserTable() {
        this(DSL.name("app_user"), null);
    }

    public <O extends Record> AppUserTable(Table<O> child, ForeignKey<O, AppUserRecord> key) {
        super(child, key, APP_USER);
    }

    @Override
    @Nonnull
    public Schema getSchema() {
        return PublicTable.PUBLIC;
    }

    @Override
    @Nonnull
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.APP_USER_MAIL_IDX, Indexes.APP_USER_USERNAME_IDX);
    }

    @Override
    @Nonnull
    public UniqueKey<AppUserRecord> getPrimaryKey() {
        return Keys.APP_USER_PKEY;
    }

    @Override
    @Nonnull
    public List<UniqueKey<AppUserRecord>> getKeys() {
        return Arrays.<UniqueKey<AppUserRecord>>asList(Keys.APP_USER_PKEY, Keys.APP_USER_MAIL_KEY, Keys.APP_USER_USERNAME_KEY);
    }

    @Override
    @Nonnull
    public AppUserTable as(String alias) {
        return new AppUserTable(DSL.name(alias), this);
    }

    @Override
    @Nonnull
    public AppUserTable as(Name alias) {
        return new AppUserTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public AppUserTable rename(String name) {
        return new AppUserTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @Nonnull
    public AppUserTable rename(Name name) {
        return new AppUserTable(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    @Nonnull
    public Row10<UUID, String, String, String, String, String, String[], String, Instant, Instant> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
