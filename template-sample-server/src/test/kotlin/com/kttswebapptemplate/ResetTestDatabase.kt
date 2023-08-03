package com.kttswebapptemplate

import com.kttswebapptemplate.database.ResetDatabase
import com.kttswebapptemplate.database.psqlDatabaseConfiguration
import com.kttswebapptemplate.database.utils.SpringLikeYamlConfigUtils

object ResetTestDatabase {

    fun resetDatabaseSchema(insertInitialData: Boolean) {
        // FIXME[tmpl] vs transaction strategy vs containers
        val databaseName =
            SpringLikeYamlConfigUtils.yamlFilesToMap(
                    ResetTestDatabase.javaClass.classLoader.getResourceAsStream(
                        "application-test.yaml"))
                .getValue("database.name")
        val configuration = psqlDatabaseConfiguration.copy(databaseName = databaseName)
        ResetDatabase.resetDatabaseSchema(configuration, insertData = insertInitialData)
    }
}
