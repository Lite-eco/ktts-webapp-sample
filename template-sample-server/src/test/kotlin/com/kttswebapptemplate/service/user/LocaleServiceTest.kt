package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.service.utils.ApplicationTaskExecutor
import com.kttswebapptemplate.service.utils.NotificationService
import java.util.Locale
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LocaleServiceTest {
    private val notificationService = NotificationService(ApplicationTaskExecutor({}, "test"))
    private val localeService = LocaleService(notificationService)

    @Test
    fun `check selectLanguage()`() {
        assertEquals(Language.En, localeService.selectLanguage(emptyList()))
        assertEquals(Language.En, localeService.selectLanguage(listOf(Locale.CHINA)))
        assertEquals(
            Language.Test,
            localeService.selectLanguage(listOf(Locale.Builder().setLanguage("test").build())))
    }
}
