package com.kttswebapptemplate.service.utils

import com.kttswebapptemplate.TemplateSampleApplication
import com.kttswebapptemplate.domain.ApplicationEnvironment
import com.kttswebapptemplate.domain.DeploymentLogId
import com.kttswebapptemplate.utils.toTypeId
import java.io.File
import java.io.FileInputStream
import java.util.Properties
import java.util.UUID

object ApplicationInstance {
    val env by lazy {
        System.getenv("ENV")?.let {
            ApplicationEnvironment.valueOf(it.replaceFirstChar { it.uppercase() })
        }
            ?: if (TemplateSampleApplication.runningApplication) {
                ApplicationEnvironment.Dev
            } else {
                ApplicationEnvironment.Test
            }
    }

    val gitRevisionProperties by lazy {
        File(System.getProperty("user.dir") + "/build.properties").let { file ->
            if (file.exists()) {
                Properties().apply { load(FileInputStream(file)) }
            } else {
                null
            }
        }
    }

    val gitRevisionLabel: String by lazy {
        gitRevisionProperties?.getProperty("shortGitRevision")
            ?: let {
                if (env !in setOf(ApplicationEnvironment.Dev, ApplicationEnvironment.Test)) {
                    throw RuntimeException("No git revision label in $env")
                }
                "[dev]"
            }
    }

    // TODO set UUID when env == dev, is simpler than using RandomService here
    val deploymentLogId = UUID.randomUUID().toTypeId<DeploymentLogId>()
}
