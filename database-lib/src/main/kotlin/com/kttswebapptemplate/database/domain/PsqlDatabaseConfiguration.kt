package com.kttswebapptemplate.database.domain

data class PsqlDatabaseConfiguration(
    val host: String,
    val port: Int,
    val databaseName: String,
    val user: String,
    val password: String?,
    val protocol: String
) {
    fun jdbcUrl() = "$protocol://$host:$port/$databaseName"
}
