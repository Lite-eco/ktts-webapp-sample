package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestStringId
import java.lang.reflect.InvocationTargetException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TemplateSampleStringIdDeserializerTest {

    @Test
    fun `test TemplateSampleStringId deserialization`() {
        assertEquals(
            TestStringId.sample(),
            Serializer.deserialize<TestStringId>("\"${TestStringId.sample().rawId}\""))
    }

    @Test
    fun `deserialization of a too short string fails`() {
        assertThrows<InvocationTargetException> {
            Serializer.deserialize<TestStringId>("\"sample\"")
        }
    }
}
