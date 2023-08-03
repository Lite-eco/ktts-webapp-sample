package com.kttswebapptemplate.config

import com.kttswebapptemplate.serialization.Serializer
import com.kttswebapptemplate.service.utils.ApplicationTaskExecutor
import com.kttswebapptemplate.service.utils.random.IdLogService
import com.kttswebapptemplate.service.utils.random.RandomService
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class ApplicationBeans {

    @Bean @Primary fun jackson() = Serializer.objectMapper

    @Bean fun okHttpClient() = OkHttpClient()

    @Bean fun idLogService() = IdLogService()

    @Bean fun randomService(idLogService: IdLogService) = RandomService(idLogService)

    @Bean
    fun taskExecutor(): TaskExecutor =
        ThreadPoolTaskExecutor().apply {
            corePoolSize = 20
            maxPoolSize = 20
            queueCapacity = 500
            threadNamePrefix = "TaskExecutor-"
            initialize()
        }

    @Bean
    fun applicationTaskExecutor(@Suppress("DEPRECATION") taskExecutor: TaskExecutor) =
        ApplicationTaskExecutor(taskExecutor, "main-executor")
}