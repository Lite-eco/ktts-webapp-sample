package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestStringId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleStringIdKeyDeserializerTest {

    @Test
    fun testDeserialization() {
        val json =
            """
            {
                "${TestStringId.sample().rawId}": "coucou"
            }
            """
        val map = Serializer.deserialize<Map<TestStringId, String>>(json)
        val expectedMap = mapOf(TestStringId.sample() to "coucou")
        assertEquals(expectedMap, map)
    }
}
