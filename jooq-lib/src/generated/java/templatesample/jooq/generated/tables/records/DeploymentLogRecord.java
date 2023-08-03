/*
 * This file is generated by jOOQ.
 */
package templatesample.jooq.generated.tables.records;


import templatesample.jooq.generated.tables.DeploymentLogTable;

import java.time.Instant;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DeploymentLogRecord extends UpdatableRecordImpl<DeploymentLogRecord> implements Record5<UUID, String, String, Instant, Instant> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.deployment_log.id</code>.
     */
    public void setId(@Nonnull UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.deployment_log.id</code>.
     */
    @Nonnull
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.deployment_log.build_version</code>.
     */
    public void setBuildVersion(@Nonnull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.deployment_log.build_version</code>.
     */
    @Nonnull
    public String getBuildVersion() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.deployment_log.system_zone_id</code>.
     */
    public void setSystemZoneId(@Nonnull String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.deployment_log.system_zone_id</code>.
     */
    @Nonnull
    public String getSystemZoneId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.deployment_log.startup_date</code>.
     */
    public void setStartupDate(@Nonnull Instant value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.deployment_log.startup_date</code>.
     */
    @Nonnull
    public Instant getStartupDate() {
        return (Instant) get(3);
    }

    /**
     * Setter for <code>public.deployment_log.shutdown_date</code>.
     */
    public void setShutdownDate(@Nullable Instant value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.deployment_log.shutdown_date</code>.
     */
    @Nullable
    public Instant getShutdownDate() {
        return (Instant) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @Nonnull
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    @Nonnull
    public Row5<UUID, String, String, Instant, Instant> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    @Nonnull
    public Row5<UUID, String, String, Instant, Instant> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    @Nonnull
    public Field<UUID> field1() {
        return DeploymentLogTable.DEPLOYMENT_LOG.ID;
    }

    @Override
    @Nonnull
    public Field<String> field2() {
        return DeploymentLogTable.DEPLOYMENT_LOG.BUILD_VERSION;
    }

    @Override
    @Nonnull
    public Field<String> field3() {
        return DeploymentLogTable.DEPLOYMENT_LOG.SYSTEM_ZONE_ID;
    }

    @Override
    @Nonnull
    public Field<Instant> field4() {
        return DeploymentLogTable.DEPLOYMENT_LOG.STARTUP_DATE;
    }

    @Override
    @Nonnull
    public Field<Instant> field5() {
        return DeploymentLogTable.DEPLOYMENT_LOG.SHUTDOWN_DATE;
    }

    @Override
    @Nonnull
    public UUID component1() {
        return getId();
    }

    @Override
    @Nonnull
    public String component2() {
        return getBuildVersion();
    }

    @Override
    @Nonnull
    public String component3() {
        return getSystemZoneId();
    }

    @Override
    @Nonnull
    public Instant component4() {
        return getStartupDate();
    }

    @Override
    @Nullable
    public Instant component5() {
        return getShutdownDate();
    }

    @Override
    @Nonnull
    public UUID value1() {
        return getId();
    }

    @Override
    @Nonnull
    public String value2() {
        return getBuildVersion();
    }

    @Override
    @Nonnull
    public String value3() {
        return getSystemZoneId();
    }

    @Override
    @Nonnull
    public Instant value4() {
        return getStartupDate();
    }

    @Override
    @Nullable
    public Instant value5() {
        return getShutdownDate();
    }

    @Override
    @Nonnull
    public DeploymentLogRecord value1(@Nonnull UUID value) {
        setId(value);
        return this;
    }

    @Override
    @Nonnull
    public DeploymentLogRecord value2(@Nonnull String value) {
        setBuildVersion(value);
        return this;
    }

    @Override
    @Nonnull
    public DeploymentLogRecord value3(@Nonnull String value) {
        setSystemZoneId(value);
        return this;
    }

    @Override
    @Nonnull
    public DeploymentLogRecord value4(@Nonnull Instant value) {
        setStartupDate(value);
        return this;
    }

    @Override
    @Nonnull
    public DeploymentLogRecord value5(@Nullable Instant value) {
        setShutdownDate(value);
        return this;
    }

    @Override
    @Nonnull
    public DeploymentLogRecord values(@Nonnull UUID value1, @Nonnull String value2, @Nonnull String value3, @Nonnull Instant value4, @Nullable Instant value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DeploymentLogRecord
     */
    public DeploymentLogRecord() {
        super(DeploymentLogTable.DEPLOYMENT_LOG);
    }

    /**
     * Create a detached, initialised DeploymentLogRecord
     */
    public DeploymentLogRecord(@Nonnull UUID id, @Nonnull String buildVersion, @Nonnull String systemZoneId, @Nonnull Instant startupDate, @Nullable Instant shutdownDate) {
        super(DeploymentLogTable.DEPLOYMENT_LOG);

        setId(id);
        setBuildVersion(buildVersion);
        setSystemZoneId(systemZoneId);
        setStartupDate(startupDate);
        setShutdownDate(shutdownDate);
    }
}
