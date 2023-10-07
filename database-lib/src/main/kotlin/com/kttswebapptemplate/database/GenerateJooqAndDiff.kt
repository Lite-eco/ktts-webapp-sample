package com.kttswebapptemplate.database

import com.kttswebapptemplate.database.jooq.JooqConfiguration
import com.kttswebapptemplate.database.jooq.JooqGeneratorStrategy
import com.kttswebapptemplate.database.utils.DatabaseUtils
import com.kttswebapptemplate.database.utils.PgQuarrelUtils
import com.kttswebapptemplate.database.utils.ShellRunner
import java.nio.file.Paths
import java.sql.DriverManager
import mu.KotlinLogging
import org.jooq.codegen.GenerationTool

fun main() {
    System.setProperty("logback.configurationFile", "logback-database-lib.xml")
    GenerateJooqAndDiff.generate()
    GenerateJooqAndDiff.logger.info { "[OK] Jooq generation" }
}

object GenerateJooqAndDiff {

    internal val logger = KotlinLogging.logger {}

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
        Paths.get(rootDir).also { logger.info { "Project dir is $it" } }
    }

    val databaseLibResourcesDir by lazy { projectDir.resolve("database-lib/src/main/resources") }

    val webServerResourcesDir by lazy {
        projectDir.resolve("template-sample-server/src/main/resources")
    }

    val sqlSchemaFilesDir by lazy { databaseLibResourcesDir.resolve("data/schema") }

    val sqlInsertFilesDir by lazy { databaseLibResourcesDir.resolve("data/insert") }

    val buildDir by lazy { projectDir.resolve("database-lib/build") }

    val sqlInitiateSchemaResultFile by lazy { buildDir.resolve("initiate-database.sql") }

    fun generate() {
        logger.info { "Generate Jooq" }
        val generationDatabaseConfiguration =
            psqlDatabaseConfiguration.copy(
                databaseName = psqlDatabaseConfiguration.databaseName + "-dbtooling")
        logger.info { "Generation via base ${generationDatabaseConfiguration.databaseName}" }
        try {
            DatabaseUtils.createDatabaseIfNeeded(generationDatabaseConfiguration)
            DriverManager.getConnection(
                    generationDatabaseConfiguration.jdbcUrl(),
                    generationDatabaseConfiguration.user,
                    generationDatabaseConfiguration.password)
                .use { ResetDatabase.resetDatabaseSchema(it, insertData = false) }
            logger.info { "Generate Jooq code" }
            GenerationTool.generate(
                JooqConfiguration.generateConfiguration(
                    conf = generationDatabaseConfiguration,
                    excludeTables = setOf("spring_session", "spring_session_attributes"),
                    generatedPackageName = "com.kttswebapptemplate.jooq",
                    generatedCodePath = projectDir.resolve("database-lib/src/generated/kotlin"),
                    generatorStrategyClass = JooqGeneratorStrategy::class))
            DatabaseUtils.createDatabaseIfNeeded(psqlDatabaseConfiguration)
            // TODO[tmpl][doc] diff will fail if Config.runDatabase does not exist
            // (not very problematic but can be better)
            PgQuarrelUtils.generateDiff(
                psqlDatabaseConfiguration, generationDatabaseConfiguration, buildDir)
            DriverManager.getConnection(
                    psqlDatabaseConfiguration.jdbcUrl(),
                    psqlDatabaseConfiguration.user,
                    psqlDatabaseConfiguration.password)
                .use { c -> ResetDatabase.resetDatabaseSchema(c, insertData = true) }
            logger.info { "Format codebase" }
            // TODO should be on generated files only
            ShellRunner.run(projectDir, "./ktfmt")
        } finally {
            DatabaseUtils.dropDatabaseIfExists(generationDatabaseConfiguration)
        }
    }
}
