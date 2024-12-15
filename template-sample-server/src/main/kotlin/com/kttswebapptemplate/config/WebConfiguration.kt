package com.kttswebapptemplate.config

import jakarta.servlet.ServletContext
import java.time.Duration
import java.time.temporal.ChronoUnit
import mu.KotlinLogging
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.EncodedResourceResolver

@Configuration
class WebConfiguration(private val env: Environment) : WebMvcConfigurer, ServletContextInitializer {

    private val logger = KotlinLogging.logger {}

    companion object {
        // TODO[tmpl] /res vs /static
        const val resourcesPath = "/static"
    }

    // cache for 4 years
    private val cacheTimeToLive = Duration.of((365 * 3 + 366).toLong(), ChronoUnit.DAYS)

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler(resourcesPath + "/**")
            .addResourceLocations("classpath:/static/", "file:static/")
            .setCachePeriod(cacheTimeToLive.seconds.toInt())
            .resourceChain(true)
            .addResolver(EncodedResourceResolver())
    }

    override fun onStartup(servletContext: ServletContext?) {
        if (env.activeProfiles.isNotEmpty()) {
            val activeProfiles = env.activeProfiles.joinToString(", ")
            logger.info { "Web application configuration, using profiles: $activeProfiles" }
        }
    }
}
