package com.kttswebapptemplate.database

import com.kttswebapptemplate.database.utils.DatabaseUtils
import com.kttswebapptemplate.database.utils.SqlDependenciesResolver
import java.nio.file.Files
import java.sql.Connection
import java.sql.DriverManager
import kotlin.io.path.absolutePathString
import mu.KotlinLogging
import org.jooq.DSLContext
import org.jooq.impl.DSL

fun main() {
    System.setProperty("logback.configurationFile", "logback-database-lib.xml")
    DatabaseUtils.createDatabaseIfNeeded(psqlDatabaseConfiguration)
    DriverManager.getConnection(
            psqlDatabaseConfiguration.jdbcUrl(),
            psqlDatabaseConfiguration.user,
            psqlDatabaseConfiguration.password)
        .use { ResetDatabase.resetDatabaseSchemas(it, insertData = true) }
    ResetDatabase.logger.info {
        "[OK] reset database \"${psqlDatabaseConfiguration.databaseName}\""
    }
}

object ResetDatabase {
    // [doc] generated directory can be deleted if there is a problem
    internal val logger = KotlinLogging.logger {}

    fun resetDatabaseSchemas(connection: Connection, insertData: Boolean): Set<String> {
        logger.info {
            "Reset database \"${psqlDatabaseConfiguration.databaseName}\", using directory: ${Directories.sqlSchemaFilesDir}"
        }
        val jooq = DSL.using(connection)
        val resolvedSql = resolveSql()
        val schemas =
            resolvedSql
                .filterIsInstance<SqlDependenciesResolver.ParseResult.CreateTable>()
                .map { it.name.schema }
                .toSet()
                .let {
                    if (it.isNotEmpty()) {
                        it
                    } else {
                        // with an empty set Jooq will generate code for information_schema &
                        // pg_catalog
                        setOf(SqlDependenciesResolver.psqlDefaultSchema)
                    }
                }
        schemas.forEach { cleanSchema(jooq, it) }
        initializeDatabase(jooq, resolvedSql)
        if (insertData) {
            insertInitialData(jooq)
        }
        return schemas
    }

    private fun cleanSchema(jooq: DSLContext, schema: String) {
        jooq.dropSchemaIfExists(schema).cascade().execute()
        jooq.createSchema(schema).execute()
    }

    private fun resolveSql(): List<SqlDependenciesResolver.ParseResult> {
        val sqlQueries =
            Directories.sqlSchemaFilesDir
                .toFile()
                .walk()
                .filter { it.extension == "sql" }
                .map {
                    val path =
                        it.absolutePath.substring(
                            Directories.sqlSchemaFilesDir.absolutePathString().length + 1)
                    SqlDependenciesResolver.SqlFile(path, Files.readString(it.toPath()))
                }
                .toList()
        return SqlDependenciesResolver.resolveSql(sqlQueries)
    }

    private fun initializeDatabase(
        jooq: DSLContext,
        resolvedSql: List<SqlDependenciesResolver.ParseResult>
    ) {
        jooq.transaction { _ -> resolvedSql.forEach { jooq.execute(it.sql) } }
        val initScript =
            "BEGIN TRANSACTION;\n" +
                resolvedSql.map { it.sql + ";" }.joinToString(separator = "\n") +
                "\nCOMMIT;"
        Files.write(Directories.sqlInitiateSchemaResultFile, initScript.toByteArray())
    }

    private fun insertInitialData(jooq: DSLContext) {
        logger.info { "Insert initial data, using directory: ${Directories.sqlInsertFilesDir}" }
        val sqlQueries =
            Directories.sqlInsertFilesDir
                .toFile()
                .walk()
                .filter { it.extension == "sql" }
                .sortedBy { it.name }
                .map { Files.readString(it.toPath()) }
                .toList()
        sqlQueries.forEach { jooq.execute(it) }
    }
}
