package com.kttswebapptemplate.database.utils

import net.sf.jsqlparser.parser.CCJSqlParserUtil
import net.sf.jsqlparser.statement.alter.Alter
import net.sf.jsqlparser.statement.create.table.CreateTable as JsqlCreateTable
import net.sf.jsqlparser.statement.create.table.ForeignKeyIndex

object SqlDependenciesResolver {

    inline class TableName(val name: String)

    // TODO test crossing tables inside a given file
    // file 1 : t1 + t2, t1 depends on t3
    // file 2 : t3 which depends on t2
    sealed class ParseResult(open val sql: String) {

        data class CreateTable(
            override val sql: String,
            val name: TableName,
            val foreignKeys: List<ForeignKeyIndex>,
        ) : ParseResult(sql)

        data class CreateType(override val sql: String) : ParseResult(sql)

        data class CreateIndex(override val sql: String) : ParseResult(sql)

        data class Alter(override val sql: String) : ParseResult(sql)
    }

    fun parseSql(sql: List<String>): List<ParseResult> =
        sql.flatMap { part ->
            val noComment =
                part.split("\n").filter { !it.startsWith("--") }.joinToString(separator = "\n")
            val parts = noComment.split(";").map { it.trim() }.filter { it.isNotBlank() }
            parts.map { parse(it) }
        }

    private fun parse(query: String) =
        // for the moment we feed jsql with anything it can parse, even if not useful (ie
        // ParseResult.Alter)
        when {
            query.startsWith("ALTER ") -> jsqlParse(query)
            query.startsWith("CREATE TABLE ") -> jsqlParse(query)
            query.startsWith("CREATE INDEX ") -> ParseResult.CreateIndex(query)
            query.startsWith("CREATE TYPE ") -> ParseResult.CreateType(query)
            else -> throw NotImplementedError("Unknown query '$query'")
        }

    private fun jsqlParse(query: String) =
        when (val parsed = CCJSqlParserUtil.parse(query)) {
            is JsqlCreateTable ->
                ParseResult.CreateTable(
                    query,
                    TableName(parsed.table.name),
                    parsed.indexes?.filterIsInstance<ForeignKeyIndex>() ?: emptyList())
            is Alter -> ParseResult.Alter(query)
            else -> throw NotImplementedError("${parsed.javaClass} $parsed")
        }

    /**
     * Resolve SQL request, ordered to respect tables dependencies. Will NOT rewrite SQL queries.
     * For crossed foreign keys, one foreign key must be in an "autonomous" ADD FOREIGN KEY query
     */
    fun resolveSql(sql: List<String>): List<ParseResult> {
        val parsed = parseSql(sql)
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
                    is ParseResult.CreateType -> true
                    is ParseResult.CreateTable -> throw RuntimeException()
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
                TableName(it.table.name) != tableChain.last()
            }
            .forEach { foreignKey ->
                if (TableName(foreignKey.table.name) !in map.keys) {
                    throw IllegalArgumentException(
                        "Table ${tableChain.last()} references ${foreignKey.table.name} which isn't described.")
                }
                if (TableName(foreignKey.table.name) == tableChain.first()) {
                    val chain = tableChain.map { it.name }.joinToString(" -> ")
                    throw IllegalArgumentException(
                        "Cyclic reference $chain -> ${foreignKey.table.name}. " +
                            "Think about using an 'ALTER TABLE [...] ADD FOREIGN KEY [...]' query.")
                }
                checkForeignKeys(tableChain + TableName(foreignKey.table.name), map)
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
                    (parsed.foreignKeys.map { TableName(it.table.name) } -
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
                val missing = parseResults.map { it.name } - resolvedTables
                throw RuntimeException("Missing $missing")
            }
            resolve + doResolveSql(parseResults, newResolvedTables)
        }
    }
}
