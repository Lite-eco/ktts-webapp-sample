package com.kttswebapptemplate

import com.kttswebapptemplate.domain.ApplicationEnvironment
import com.kttswebapptemplate.service.utils.ApplicationInstance
import mu.KotlinLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession

@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
@EnableJdbcHttpSession
class TemplateSampleApplication

fun main(args: Array<String>) {
    ApplicationInstance.runningApplication = true
    val lowercaseEnv = ApplicationInstance.env.name.lowercase()
    System.setProperty("logging.config", "classpath:logback-webapp-$lowercaseEnv.xml")
    val app = SpringApplication(TemplateSampleApplication::class.java)
    app.setDefaultProperties(
        mapOf(
            "spring.profiles.default" to
                if (ApplicationInstance.env == ApplicationEnvironment.Dev) {
                    val userProfile = lowercaseEnv + "-" + System.getProperty("user.name")
                    "$lowercaseEnv,$userProfile"
                } else {
                    lowercaseEnv
                }))
    app.run(*args)
    KotlinLogging.logger {}.info { "Environment is [${ApplicationInstance.env}]" }
}
