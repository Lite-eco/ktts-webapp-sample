package com.kttswebapptemplate.config

import com.kttswebapptemplate.domain.Mail
import java.time.ZoneId

object ApplicationConstants {
    // FIXME[tmpl] remove !
    const val springMvcModelKeyStackTrace = "stackTrace"

    // TODO[tmpl] /res vs /static
    const val resourcesPath = "/static"

    // TODO[tmpl] naming... Monitoring ?
    val applicationMailSenderContact =
        Mail.Contact("TemplateSample", "dev+log-template-sample@example.com")

    val parisZoneId = ZoneId.of("Europe/Paris")
}
