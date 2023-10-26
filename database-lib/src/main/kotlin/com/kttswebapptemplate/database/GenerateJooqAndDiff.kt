package com.kttswebapptemplate.database

import com.kttswebapptemplate.database.jooq.JooqConfiguration
import com.kttswebapptemplate.database.jooq.JooqGeneratorStrategy
import com.kttswebapptemplate.database.utils.DatabaseUtils
import com.kttswebapptemplate.database.utils.PgQuarrelUtils
import com.kttswebapptemplate.database.utils.ShellRunner
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
                    generatedCodePath = Directories.generatedDir.resolve("kotlin"),
                    generatorStrategyClass = JooqGeneratorStrategy::class))
            DatabaseUtils.createDatabaseIfNeeded(psqlDatabaseConfiguration)
            // TODO[tmpl][doc] diff will fail if Config.runDatabase does not exist
            // (not very problematic but can be better)
            PgQuarrelUtils.generateDiff(
                psqlDatabaseConfiguration, generationDatabaseConfiguration, Directories.buildDir)
            DriverManager.getConnection(
                    psqlDatabaseConfiguration.jdbcUrl(),
                    psqlDatabaseConfiguration.user,
                    psqlDatabaseConfiguration.password)
                .use { c -> ResetDatabase.resetDatabaseSchema(c, insertData = true) }
            logger.info { "Format codebase" }
            // TODO should be on generated files only
            ShellRunner.run(Directories.projectDir, "./ktfmt")
        } finally {
            DatabaseUtils.dropDatabaseIfExists(generationDatabaseConfiguration)
        }
    }
}
