/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated;


import com.kttswebapptemplate.jooq.generated.tables.AppUserTable;
import com.kttswebapptemplate.jooq.generated.tables.MailLogTable;
import com.kttswebapptemplate.jooq.generated.tables.UserMailLogTable;
import com.kttswebapptemplate.jooq.generated.tables.UserSessionLogTable;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index APP_USER_MAIL_IDX = Internal.createIndex(DSL.name("app_user_mail_idx"), AppUserTable.APP_USER, new OrderField[] { AppUserTable.APP_USER.MAIL }, false);
    public static final Index MAIL_LOG_USER_ID_IDX = Internal.createIndex(DSL.name("mail_log_user_id_idx"), MailLogTable.MAIL_LOG, new OrderField[] { MailLogTable.MAIL_LOG.USER_ID }, false);
    public static final Index USER_MAIL_LOG_USER_ID_IDX = Internal.createIndex(DSL.name("user_mail_log_user_id_idx"), UserMailLogTable.USER_MAIL_LOG, new OrderField[] { UserMailLogTable.USER_MAIL_LOG.USER_ID }, false);
    public static final Index USER_SESSION_LOG_USER_ID_IDX = Internal.createIndex(DSL.name("user_session_log_user_id_idx"), UserSessionLogTable.USER_SESSION_LOG, new OrderField[] { UserSessionLogTable.USER_SESSION_LOG.USER_ID }, false);
}
