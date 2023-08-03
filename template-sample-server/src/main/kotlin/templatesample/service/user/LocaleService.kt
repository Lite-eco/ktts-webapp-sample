package templatesample.service.user

import java.util.Enumeration
import java.util.Locale
import org.springframework.stereotype.Service
import templatesample.domain.Language
import templatesample.service.utils.NotificationService

@Service
class LocaleService(private val notificationService: NotificationService) {

    fun selectLanguage(locales: Enumeration<Locale>?): Language {
        if (locales == null) {
            return Language.En
        }
        for (locale in locales) {
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
