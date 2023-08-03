package com.kttswebapptemplate.jooqlib

import com.kttswebapptemplate.jooqlib.utils.SpringLikeYamlConfigUtils
import jooqutils.DatabaseConfiguration
import kotlin.io.path.exists
import kotlin.io.path.inputStream

object Configuration {

    val configuration by lazy {
        val additionalConfig =
            System.getenv("TEMPLATE_SAMPLE_DEV_ADDITIONAL_CONFIG")
                ?: ("dev-" + System.getenv("USER"))
        configuration("application-dev.yaml", "application-$additionalConfig.yaml")
    }

    private fun configuration(vararg configurationFiles: String): DatabaseConfiguration {
        val config =
            SpringLikeYamlConfigUtils.yamlFilesToMap(
                *configurationFiles
                    .mapNotNull {
                        GenerateJooqAndDiff.webServerResourcesDir.resolve(it).let {
                            if (it.exists()) it.inputStream() else null
                        }
                    }
                    .toTypedArray())
        val host = config.getValue("database.host")
        val allowRemoteHost =
            System.getenv("TEMPLATE_SAMPLE_DB_TOOLING_ALLOW_REMOTE_HOST") == "true"
        // TODO[tmpl] test ip not extension !
        if (host.endsWith(".com") && !allowRemoteHost) {
            throw RuntimeException("Warning run database operations on $host")
        }
        val databaseName = config.getValue("database.name")
        if ("prod" in databaseName) {
            throw RuntimeException("Warning run database operations on $databaseName")
        }
        return DatabaseConfiguration(
            driver = DatabaseConfiguration.Driver.psql,
            host = host,
            port = config.getValue("database.port").toInt(),
            databaseName = databaseName,
            user = config.getValue("database.user"),
            password = config["database.password"],
            schemas = setOf("public"))
    }
}
