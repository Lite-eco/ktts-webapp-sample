/*
 * This file is generated by jOOQ.
 */
package templatesample.jooq.generated;


import templatesample.jooq.generated.tables.AppUserTable;
import templatesample.jooq.generated.tables.CommandLogTable;
import templatesample.jooq.generated.tables.DeploymentLogTable;
import templatesample.jooq.generated.tables.MagicLinkTokenTable;
import templatesample.jooq.generated.tables.SentMailLogTable;
import templatesample.jooq.generated.tables.UserFileTable;
import templatesample.jooq.generated.tables.UserMailLogTable;
import templatesample.jooq.generated.tables.UserSessionLogTable;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PublicTable extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final PublicTable PUBLIC = new PublicTable();

    /**
     * The table <code>public.app_user</code>.
     */
    public final AppUserTable APP_USER = AppUserTable.APP_USER;

    /**
     * The table <code>public.command_log</code>.
     */
    public final CommandLogTable COMMAND_LOG = CommandLogTable.COMMAND_LOG;

    /**
     * The table <code>public.deployment_log</code>.
     */
    public final DeploymentLogTable DEPLOYMENT_LOG = DeploymentLogTable.DEPLOYMENT_LOG;

    /**
     * The table <code>public.magic_link_token</code>.
     */
    public final MagicLinkTokenTable MAGIC_LINK_TOKEN = MagicLinkTokenTable.MAGIC_LINK_TOKEN;

    /**
     * The table <code>public.sent_mail_log</code>.
     */
    public final SentMailLogTable SENT_MAIL_LOG = SentMailLogTable.SENT_MAIL_LOG;

    /**
     * The table <code>public.user_file</code>.
     */
    public final UserFileTable USER_FILE = UserFileTable.USER_FILE;

    /**
     * The table <code>public.user_mail_log</code>.
     */
    public final UserMailLogTable USER_MAIL_LOG = UserMailLogTable.USER_MAIL_LOG;

    /**
     * The table <code>public.user_session_log</code>.
     */
    public final UserSessionLogTable USER_SESSION_LOG = UserSessionLogTable.USER_SESSION_LOG;

    /**
     * No further instances allowed
     */
    private PublicTable() {
        super("public", null);
    }


    @Override
    @Nonnull
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    @Nonnull
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            AppUserTable.APP_USER,
            CommandLogTable.COMMAND_LOG,
            DeploymentLogTable.DEPLOYMENT_LOG,
            MagicLinkTokenTable.MAGIC_LINK_TOKEN,
            SentMailLogTable.SENT_MAIL_LOG,
            UserFileTable.USER_FILE,
            UserMailLogTable.USER_MAIL_LOG,
            UserSessionLogTable.USER_SESSION_LOG);
    }
}
