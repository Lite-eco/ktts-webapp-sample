package com.kttswebapptemplate.service.mail

import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.MailData

object MailTemplates {

    fun mailSubjectAndData(data: MailData, language: Language): Pair<String, String> =
        when (data) {
            is MailData.AccountMailValidation -> accountMailValidation(data, language)
            is MailData.LostPassword -> lostPassword(data, language)
            is MailData.LostPasswordNoAccount -> noAccount(data, language)
        }

    fun accountMailValidation(data: MailData.AccountMailValidation, language: Language) =
        when (language) {
            Language.En ->
                "Mail validation" to
                    """
                Hi ${data.displayName},
                <br /><br />
                Please validate your mail <a href="${data.url.path}">${data.url.path}</a>.
            """
                        .trimIndent()
            Language.Test -> TODO()
        }

    fun lostPassword(data: MailData.LostPassword, language: Language) =
        when (language) {
            Language.En ->
                "Lost password" to
                    """
                Hi ${data.displayName},
                <br /><br />
                A lost password request has been made for your account.
                <br /><br />
                You can reset your password here: <a href="${data.url.path}">${data.url.path}</a>.
            """
                        .trimIndent()
            Language.Test -> TODO()
        }

    fun noAccount(data: MailData.LostPasswordNoAccount, language: Language) =
        when (language) {
            Language.En ->
                "Lost password - no account" to
                    """
                Hi,
                <br /><br />
                A lost password request has been made for mail ${data.mail} but no account is registered for it.
                <br /><br />
                You can create one here: <a href="${data.url.path}">${data.url.path}</a>.
            """
                        .trimIndent()
            Language.Test -> TODO()
        }
}
