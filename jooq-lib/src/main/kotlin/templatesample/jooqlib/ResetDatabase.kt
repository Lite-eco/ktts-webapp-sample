package templatesample.jooqlib

import jooqutils.DatabaseCleaner
import jooqutils.DatabaseConfiguration
import jooqutils.DatabaseInitializer
import mu.KotlinLogging
import templatesample.jooqlib.GenerateJooqAndDiff.sqlCleanResultFile
import templatesample.jooqlib.GenerateJooqAndDiff.sqlInitiateSchemaResultFile
import templatesample.jooqlib.GenerateJooqAndDiff.sqlInsertFilesDir
import templatesample.jooqlib.GenerateJooqAndDiff.sqlSchemaFilesDir

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
            "Reset database \"${configuration.databaseName}\", using directory : $sqlSchemaFilesDir"
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
        logger.info { "Insert initial data, using directory : $sqlInsertFilesDir" }
        DatabaseInitializer.insert(configuration, sqlInsertFilesDir)
    }
}
