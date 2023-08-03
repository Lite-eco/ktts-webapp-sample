package com.kttswebapptemplate.database

import com.kttswebapptemplate.database.GenerateJooqAndDiff.sqlInitiateSchemaResultFile
import com.kttswebapptemplate.database.GenerateJooqAndDiff.sqlInsertFilesDir
import com.kttswebapptemplate.database.GenerateJooqAndDiff.sqlSchemaFilesDir
import com.kttswebapptemplate.database.domain.PsqlDatabaseConfiguration
import com.kttswebapptemplate.database.utils.DatasourcePool
import com.kttswebapptemplate.database.utils.ShellRunner
import com.kttswebapptemplate.database.utils.SqlDependenciesResolver
import java.nio.file.Files
import mu.KotlinLogging

fun main() {
    System.setProperty("logback.configurationFile", "logback-database-lib.xml")
    ResetDatabase.resetDatabaseSchema(psqlDatabaseConfiguration, insertData = true)
    ResetDatabase.logger.info {
        "[OK] reset database \"${psqlDatabaseConfiguration.databaseName}\""
    }
}

object ResetDatabase {
    // [doc] generated directory can be deleted if there is a problem
    internal val logger = KotlinLogging.logger {}

    fun resetDatabaseSchema(configuration: PsqlDatabaseConfiguration, insertData: Boolean) {
        logger.info {
            "Reset database \"${configuration.databaseName}\", using directory: $sqlSchemaFilesDir"
        }
        createDatabase(configuration, fails = false)
        cleanSchema(configuration)
        initializeSchema(configuration)
        if (insertData) {
            insertInitialData(psqlDatabaseConfiguration)
        }
    }

    fun dropDatabase(configuration: PsqlDatabaseConfiguration) {
        logger.info { "Drop database \"${configuration.databaseName}\"" }
        ShellRunner.run("dropdb", configuration.databaseName).let {
            if (it.result != 0) {
                throw RuntimeException(
                    "Could not dropdb : ${it.result}\n${it.errorOutput.joinToString(separator = "\n")}")
            }
        }
    }

    private fun createDatabase(configuration: PsqlDatabaseConfiguration, fails: Boolean) {
        logger.info { "Create database \"${configuration.databaseName}\"" }
        ShellRunner.run("createdb", configuration.databaseName).let {
            if (fails && it.result != 0) {
                throw RuntimeException(
                    "Could not createdb : ${it.result}\n${it.errorOutput.joinToString(separator = "\n")}")
            }
        }
    }

    private fun cleanSchema(configuration: PsqlDatabaseConfiguration) {
        logger.info { "Clean database \"${configuration.databaseName}\"" }
        DatasourcePool.get(configuration).connection.createStatement().use {
            it.execute("DROP SCHEMA ${configuration.schema} CASCADE;")
            it.execute("CREATE SCHEMA ${configuration.schema};")
        }
    }

    private fun initializeSchema(configuration: PsqlDatabaseConfiguration) {
        logger.info { "Initialize database schema \"${configuration.databaseName}\"" }
        val sqlQueries =
            sqlSchemaFilesDir
                .toFile()
                .walk()
                .filter { it.extension == "sql" }
                .map { Files.readString(it.toPath()) }
                .toList()
        val resolved = SqlDependenciesResolver.resolveSql(sqlQueries)
        val initScript =
            "BEGIN TRANSACTION;\n" + resolved.joinToString(separator = "\n") + "COMMIT;"
        DatasourcePool.get(configuration).connection.createStatement().use {
            it.execute(initScript)
        }
        Files.write(sqlInitiateSchemaResultFile, initScript.toByteArray())
    }

    private fun insertInitialData(configuration: PsqlDatabaseConfiguration) {
        logger.info { "Insert initial data, using directory: $sqlInsertFilesDir" }
        val sqlFiles =
            sqlInsertFilesDir
                .toFile()
                .walk()
                .filter { it.extension == "sql" }
                .toList()
                .sortedBy { it.name }
        sqlFiles.forEach { sqlFile ->
            logger.info { "Inject ${sqlFile.name}" }
            val sql = Files.readString(sqlFile.toPath())
            DatasourcePool.get(configuration).connection.createStatement().use { it.execute(sql) }
        }
    }
}
