package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.service.utils.NotificationService
import java.util.Locale
import org.springframework.stereotype.Service

@Service
class LocaleService(private val notificationService: NotificationService) {

    fun selectLanguage(locales: List<Locale>): Language {
        if (locales.isEmpty()) {
            return Language.En
        }
        locales.forEach { locale ->
            val language = Language.values().find { it.name == locale.language }
            if (language != null) {
                return language
            }
        }
        notificationService.notify(
            "No locale found in user locales $locales", NotificationService.Channel.Info)
        return Language.En
    }
}
