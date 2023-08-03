package com.kttswebapptemplate.jooqlib

import com.kttswebapptemplate.jooqlib.GenerateJooqAndDiff.sqlCleanResultFile
import com.kttswebapptemplate.jooqlib.GenerateJooqAndDiff.sqlInitiateSchemaResultFile
import com.kttswebapptemplate.jooqlib.GenerateJooqAndDiff.sqlInsertFilesDir
import com.kttswebapptemplate.jooqlib.GenerateJooqAndDiff.sqlSchemaFilesDir
import jooqutils.DatabaseCleaner
import jooqutils.DatabaseConfiguration
import jooqutils.DatabaseInitializer
import mu.KotlinLogging

fun main() {
    System.setProperty("logback.configurationFile", "logback-jooq-lib.xml")
    ResetDatabase.resetDatabaseSchema(Configuration.configuration)
    ResetDatabase.insertInitialData(Configuration.configuration)
    ResetDatabase.logger.info {
        "[OK] reset database \"${Configuration.configuration.databaseName}\""
    }
}

object ResetDatabase {
    // [doc] generated directory can be deleted if there is a problem
    internal val logger = KotlinLogging.logger {}

    fun resetDatabaseSchema(configuration: DatabaseConfiguration) {
        logger.info {
            "Reset database \"${configuration.databaseName}\", using directory: $sqlSchemaFilesDir"
        }
        logger.info { "Create database \"${configuration.databaseName}\"" }
        DatabaseInitializer.createDb(configuration)
        logger.info { "Clean database \"${configuration.databaseName}\"" }
        DatabaseCleaner.clean(configuration, sqlCleanResultFile)
        logger.info { "Initialize database schema \"${configuration.databaseName}\"" }
        DatabaseInitializer.initializeSchema(
            configuration, sqlSchemaFilesDir, sqlInitiateSchemaResultFile)
    }

    fun insertInitialData(configuration: DatabaseConfiguration) {
        logger.info { "Insert initial data, using directory: $sqlInsertFilesDir" }
        DatabaseInitializer.insert(configuration, sqlInsertFilesDir)
    }
}
