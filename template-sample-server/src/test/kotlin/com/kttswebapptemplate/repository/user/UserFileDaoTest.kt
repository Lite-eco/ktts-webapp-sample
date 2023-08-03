package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.ResetTestDatabase
import com.kttswebapptemplate.jooq.generated.Tables.USER_FILE
import org.jooq.TableField
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.reflections.ReflectionUtils

internal class UserFileDaoTest {

    @BeforeEach
    fun init() {
        ResetTestDatabase.resetDatabaseSchema(false)
    }

    @Test
    fun `test fields exhaustiveness`() {
        val jooqFields = ReflectionUtils.getAllFields(USER_FILE.javaClass)
        val fields =
            jooqFields
                .filter { it.type == TableField::class.java }
                .map { it.get(USER_FILE) }
                .toSet()
        val daoFields = UserFileDao.UserFileField.values().map { it.field }.toSet()
        assertEquals(fields, daoFields)
    }
}