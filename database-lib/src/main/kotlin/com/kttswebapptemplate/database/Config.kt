package com.kttswebapptemplate.database

import com.kttswebapptemplate.database.domain.PsqlDatabaseConfiguration
import com.kttswebapptemplate.database.utils.SpringLikeYamlConfigUtils
import kotlin.io.path.exists
import kotlin.io.path.inputStream

val psqlDatabaseConfiguration: PsqlDatabaseConfiguration by lazy {
    fun configuration(vararg configurationFiles: String): PsqlDatabaseConfiguration {
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
        return PsqlDatabaseConfiguration(
            host = host,
            port = config.getValue("database.port").toInt(),
            databaseName = databaseName,
            user = config.getValue("database.user"),
            password = config.getValueOrNull("database.password"),
            schema = "public")
    }
    val additionalConfig =
        System.getenv("TEMPLATE_SAMPLE_DEV_ADDITIONAL_CONFIG") ?: ("dev-" + System.getenv("USER"))
    configuration("application-dev.yaml", "application-$additionalConfig.yaml")
}
