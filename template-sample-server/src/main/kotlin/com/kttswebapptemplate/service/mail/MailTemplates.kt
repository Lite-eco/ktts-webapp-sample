package com.kttswebapptemplate.service.mail

import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.MailData

object MailTemplates {

    fun mailSubjectAndData(data: MailData, language: Language): Pair<String, String> =
        when (data) {
            is MailData.AccountMailValidation -> accountMailValidation(data, language)
            is MailData.LostPassword -> TODO()
        }

    fun accountMailValidation(data: MailData.AccountMailValidation, language: Language) =
        when (language) {
            Language.En ->
                "Mail validation" to
                    """
                Hi ${data.displayName},
                
                Please validate your mail ${data.url.path}.
            """
                        .trimIndent()
            Language.Test -> TODO()
        }
}
