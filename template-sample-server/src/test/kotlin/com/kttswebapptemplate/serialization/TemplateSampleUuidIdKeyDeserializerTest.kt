package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestIds.sampleNodesUuidId
import com.kttswebapptemplate.domain.TestUuidId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleUuidIdKeyDeserializerTest {

    @Test
    fun testDeserialization() {
        val json =
            """
            {
                "00000000000000000000000000000000": "coucou"
            }
            """
        val map = Serializer.deserialize<Map<TestUuidId, String>>(json)
        val expectedMap = mapOf(sampleNodesUuidId<TestUuidId>() to "coucou")
        assertEquals(expectedMap, map)
    }
}
