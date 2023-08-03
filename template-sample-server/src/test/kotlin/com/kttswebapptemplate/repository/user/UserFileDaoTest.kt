package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.jooq.generated.Tables.USER_FILE
import org.jooq.Field
import org.jooq.TableField
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.reflections.ReflectionUtils

internal class UserFileDaoTest {

    @Test
    fun `test fields exhaustiveness`() {
        val jooqFields =
            ReflectionUtils.getAllFields(USER_FILE.javaClass)
                .filter { it.type == TableField::class.java }
                .map { it.get(USER_FILE) as Field<*> }
        val nonDataFields = jooqFields.filter { it.type != ByteArray::class.java }.toSet()
        assertEquals(nonDataFields, UserFileDao.nonDataFields)
    }
}
