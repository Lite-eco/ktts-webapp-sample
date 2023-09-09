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
        val languages = locales.map { it.language }
        return Language.entries.firstOrNull { it.name.lowercase() in languages }
            ?: let {
                notificationService.notify(
                    "No locale found in user locales $locales", NotificationService.Channel.Info)
                Language.En
            }
    }
}
