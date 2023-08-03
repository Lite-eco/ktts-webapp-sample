/*
 * This file is generated by jOOQ.
 */
package com.kttswebapptemplate.jooq.generated.indexes

import com.kttswebapptemplate.jooq.generated.tables.AppUserTable
import com.kttswebapptemplate.jooq.generated.tables.MailLogTable
import com.kttswebapptemplate.jooq.generated.tables.UserMailLogTable
import com.kttswebapptemplate.jooq.generated.tables.UserSessionLogTable
import org.jooq.Index
import org.jooq.impl.DSL
import org.jooq.impl.Internal

// -------------------------------------------------------------------------
// INDEX definitions
// -------------------------------------------------------------------------

val APP_USER_MAIL_IDX: Index =
    Internal.createIndex(
        DSL.name("app_user_mail_idx"),
        AppUserTable.APP_USER,
        arrayOf(AppUserTable.APP_USER.MAIL),
        false)
val MAIL_LOG_USER_ID_IDX: Index =
    Internal.createIndex(
        DSL.name("mail_log_user_id_idx"),
        MailLogTable.MAIL_LOG,
        arrayOf(MailLogTable.MAIL_LOG.USER_ID),
        false)
val USER_MAIL_LOG_USER_ID_IDX: Index =
    Internal.createIndex(
        DSL.name("user_mail_log_user_id_idx"),
        UserMailLogTable.USER_MAIL_LOG,
        arrayOf(UserMailLogTable.USER_MAIL_LOG.USER_ID),
        false)
val USER_SESSION_LOG_USER_ID_IDX: Index =
    Internal.createIndex(
        DSL.name("user_session_log_user_id_idx"),
        UserSessionLogTable.USER_SESSION_LOG,
        arrayOf(UserSessionLogTable.USER_SESSION_LOG.USER_ID),
        false)
