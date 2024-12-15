package com.kttswebapptemplate.database

import java.nio.file.Path
import mu.KotlinLogging

object Directories {
    val logger = KotlinLogging.logger {}

    val projectDir by lazy {
        // contexts: run in intellij, gradle (with different root dirs), tests
        val userDir = System.getProperty("user.dir")
        val rootDir = let {
            listOf(
                    "/database-lib",
                    // (in tests)
                    "/template-sample-server",
                )
                .firstOrNull { userDir.endsWith(it) }
                ?.let { userDir.dropLast(it.length) } ?: userDir
        }
        Path.of(rootDir).also { logger.info { "Project dir is $it" } }
    }

    val generatedDir by lazy { projectDir.resolve("database-lib/src/generated") }

    val sqlSchemaFilesDir by lazy {
        projectDir.resolve("database-lib/src/main/resources/data/schema")
    }

    val sqlInsertFilesDir by lazy {
        projectDir.resolve("database-lib/src/main/resources/data/insert")
    }

    val buildDir by lazy { projectDir.resolve("database-lib/build") }

    val sqlInitiateSchemaResultFile by lazy { buildDir.resolve("initiate-database.sql") }

    val webServerResourcesDir by lazy {
        projectDir.resolve("template-sample-server/src/main/resources")
    }
}
