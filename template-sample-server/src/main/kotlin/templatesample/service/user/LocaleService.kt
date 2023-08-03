package templatesample.service.user

import java.util.Locale
import org.springframework.stereotype.Service
import templatesample.domain.Language
import templatesample.service.utils.NotificationService

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
