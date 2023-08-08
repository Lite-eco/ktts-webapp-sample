package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestStringId
import com.kttswebapptemplate.utils.toTypeId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TemplateSampleStringIdSerializerTest {

    @Test
    fun `test TemplateSampleStringId serialization`() {
        val id = TestStringId.sample()
        assertEquals("\"${TestStringId.sample().rawId}\"", Serializer.serialize(id))
    }

    @Test
    fun `serialization of a too short string fails`() {
        assertThrows<IllegalArgumentException> {
            Serializer.serialize("sample".toTypeId<TestStringId>())
        }
    }
}
