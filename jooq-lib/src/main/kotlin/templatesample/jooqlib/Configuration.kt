package templatesample.jooqlib

import templatesample.jooqlib.utils.SpringLikeYamlConfigUtils
import java.io.FileInputStream
import java.io.InputStream
import jooqutils.DatabaseConfiguration

object Configuration {

    val configuration by lazy {
        val additionalConfig =
            System.getenv("TEMPLATE_SAMPLE_DEV_ADDITIONAL_CONFIG") ?: ("dev-" + System.getenv("USER"))
        configuration("application-dev.yaml", "application-$additionalConfig.yaml")
    }

    fun configuration(vararg configurationFiles: String): DatabaseConfiguration {
        val config =
            SpringLikeYamlConfigUtils.yamlFilesToMap(
                *configurationFiles.map { streamConfigurationFile(it) }.toTypedArray())
        val host = config.getValue("database.host")
        if (host.endsWith(".com")) {
            throw RuntimeException("Warning run database operations on $host")
        }
        return DatabaseConfiguration(
            DatabaseConfiguration.Driver.psql,
            host,
            config.getValue("database.port").toInt(),
            config.getValue("database.name"),
            config.getValue("database.user"),
            config["database.password"],
            setOf("public"),
            "/usr/local/bin",
            config["pgquarrel"])
    }

    private fun streamConfigurationFile(file: String): InputStream {
        val resourceResolve = GenerateJooqAndDiff.webServerResourcesDir.resolve(file).toFile()
        return if (resourceResolve.exists()) {
            FileInputStream(resourceResolve)
        } else {
            Configuration.javaClass.classLoader.getResourceAsStream(file)
        }
    }
}
