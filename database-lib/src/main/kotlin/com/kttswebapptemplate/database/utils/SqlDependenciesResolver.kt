package com.kttswebapptemplate.database.utils

import mu.KotlinLogging
import net.sf.jsqlparser.JSQLParserException
import net.sf.jsqlparser.parser.CCJSqlParserUtil
import net.sf.jsqlparser.schema.Table
import net.sf.jsqlparser.statement.alter.Alter
import net.sf.jsqlparser.statement.create.table.CreateTable as JsqlCreateTable
import net.sf.jsqlparser.statement.create.table.CreateTable
import net.sf.jsqlparser.statement.create.table.ForeignKeyIndex

object SqlDependenciesResolver {

    internal val logger = KotlinLogging.logger {}

    const val psqlDefaultSchema = "public"

    data class TableName(val schema: String, val name: String) {
        companion object {
            fun from(table: Table) = TableName(table.schemaName ?: psqlDefaultSchema, table.name)
        }
    }

    data class SqlFile(val path: String, val sql: String)

    // TODO test crossing tables inside a given file
    // file 1 : t1 + t2, t1 depends on t3
    // file 2 : t3 which depends on t2
    sealed class ParseResult(open val file: SqlFile) {
        data class Alter(override val file: SqlFile) : ParseResult(file)

        data class CreateIndex(override val file: SqlFile) : ParseResult(file)

        data class CreateTable(
            override val file: SqlFile,
            val name: TableName,
            val foreignKeys: List<ForeignKeyIndex>,
        ) : ParseResult(file)

        data class CreateType(override val file: SqlFile) : ParseResult(file)

        data class CreateView(override val file: SqlFile) : ParseResult(file)
    }

    fun parseSql(sqlFiles: List<SqlFile>): List<ParseResult> =
        sqlFiles.flatMap { sqlFile ->
            val noComment =
                sqlFile.sql
                    .split("\n")
                    .filter { !it.startsWith("--") }
                    .joinToString(separator = "\n")
            val parts = noComment.split(";").map { it.trim() }.filter { it.isNotBlank() }
            parts.map { parse(SqlFile(sqlFile.path, it)) }
        }

    private fun parse(sqlFile: SqlFile) =
        // for the moment we feed jsql with anything it can parse, even if not useful (ie
        // ParseResult.Alter)
        when {
            sqlFile.sql.startsWith("ALTER ") -> jsqlParse(sqlFile)
            sqlFile.sql.startsWith("CREATE TABLE ") -> jsqlParse(sqlFile)
            sqlFile.sql.startsWith("CREATE INDEX ") -> ParseResult.CreateIndex(sqlFile)
            sqlFile.sql.startsWith("CREATE TYPE ") -> ParseResult.CreateType(sqlFile)
            sqlFile.sql.startsWith("CREATE VIEW ") -> ParseResult.CreateView(sqlFile)
            sqlFile.sql.startsWith("CREATE SCHEMA ") ->
                throw IllegalArgumentException(
                    "Postgresql schemas are deducted from table names, remove 'create schema' in \"${sqlFile.path}\"")
            else -> throw NotImplementedError("Unknown query in ${sqlFile.path} '${sqlFile.sql}'")
        }

    private fun jsqlParse(sqlFile: SqlFile) =
        try {
            when (val parsed = CCJSqlParserUtil.parse(sqlFile.sql)) {
                is JsqlCreateTable ->
                    ParseResult.CreateTable(
                        sqlFile, TableName.from(parsed.table), foreignKeys(parsed, sqlFile))
                is Alter -> ParseResult.Alter(sqlFile)
                else -> throw NotImplementedError("${parsed.javaClass} $parsed")
            }
        } catch (e: JSQLParserException) {
            throw IllegalArgumentException("Parsing error in ${sqlFile.path}", e)
        }

    private fun foreignKeys(parsed: CreateTable, sqlFile: SqlFile): List<ForeignKeyIndex> {
        parsed.columnDefinitions?.map {
            val references = it.columnSpecs?.filter { it == "REFERENCES" }
            if (references?.isNotEmpty() == true) {
                // TODO handle those =]
                throw IllegalArgumentException(
                    "Can't handle direct SQL references (use FOREIGN KEY syntax instead). " +
                        "Found one in file ${sqlFile.path} table \"${parsed.table.name}\" column \"${it.columnName}\"")
            }
        }
        return parsed.indexes?.filterIsInstance<ForeignKeyIndex>() ?: emptyList()
    }

    /**
     * Resolve SQL request, ordered to respect tables dependencies. Will NOT rewrite SQL queries.
     * For crossed foreign keys, one foreign key must be in an "autonomous" ADD FOREIGN KEY query
     */
    fun resolveSql(sqlFiles: List<SqlFile>): List<ParseResult> {
        val parsed = parseSql(sqlFiles)
        val (createTables, otherSql) =
            @Suppress("UNCHECKED_CAST")
            (parsed.partition { it is ParseResult.CreateTable }
                as Pair<List<ParseResult.CreateTable>, List<ParseResult>>)
        // check references exist & no cyclic reference
        let {
            val map: Map<TableName, ParseResult.CreateTable> = createTables.associateBy { it.name }
            map.keys.forEach { checkForeignKeys(listOf(it), map) }
        }
        val resolved = doResolveSql(createTables.toSet())
        val (beforeCreateTables, afterCreateTables) =
            otherSql.partition {
                when (it) {
                    is ParseResult.Alter -> false
                    is ParseResult.CreateIndex -> false
                    is ParseResult.CreateTable -> throw RuntimeException()
                    is ParseResult.CreateType -> true
                    is ParseResult.CreateView -> false
                }
            }
        return beforeCreateTables + resolved + afterCreateTables
    }

    private fun checkForeignKeys(
        tableChain: List<TableName>,
        map: Map<TableName, ParseResult.CreateTable>
    ) {
        val parseResult = map.getValue(tableChain.last())
        parseResult.foreignKeys
            .filter {
                // table referencing itself is authorized
                TableName.from(it.table) != tableChain.last()
            }
            .forEach { foreignKey ->
                if (TableName.from(foreignKey.table) !in map.keys) {
                    throw IllegalArgumentException(
                        "Table ${tableChain.last()} references ${foreignKey.table.name} which isn't described.")
                }
                if (TableName.from(foreignKey.table) == tableChain.first()) {
                    val chain = tableChain.map { it.name }.joinToString(" -> ")
                    throw IllegalArgumentException(
                        "Cyclic reference $chain -> ${foreignKey.table.name}. " +
                            "Think about using an 'ALTER TABLE [...] ADD FOREIGN KEY [...]' query.")
                }
                checkForeignKeys(tableChain + TableName.from(foreignKey.table), map)
            }
    }

    private fun doResolveSql(
        parseResults: Set<ParseResult.CreateTable>,
        resolvedTables: Set<TableName> = emptySet()
    ): List<ParseResult.CreateTable> {
        val resolve =
            parseResults
                .filter { it.name !in resolvedTables }
                .filter { parsed ->
                    (parsed.foreignKeys.map { TableName.from(it.table) } -
                            resolvedTables -
                            // table can reference itselft
                            parsed.name)
                        .isEmpty()
                }
        val newResolvedTables = resolvedTables + resolve.map { it.name }
        return if (parseResults.size == newResolvedTables.size) {
            resolve
        } else {
            if (resolve.isEmpty()) {
                val duplicates =
                    parseResults
                        .groupBy { it.name }
                        .filter { it.value.size > 1 }
                        .map { it.key.name to it.value.map { it.file.path } }
                if (duplicates.isNotEmpty()) {
                    throw RuntimeException(
                        "Tables duplicates: \n* ${duplicates.map { "${it.first} in files ${it.second.joinToString(separator = ", ")}"}.joinToString(separator = "\n* ")}")
                }
                val missing = parseResults.map { it.name } - resolvedTables
                throw RuntimeException("Missing $missing")
            }
            resolve + doResolveSql(parseResults, newResolvedTables)
        }
    }
}
