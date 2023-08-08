package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestIds.sampleNodesUuidId
import com.kttswebapptemplate.domain.TestUuidId
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

internal class TemplateSampleUuidIdKeySerializerTest {

    @Test
    fun testSerialization() {
        val map = mapOf(sampleNodesUuidId<TestUuidId>() to "coucou")
        val json = Serializer.serialize(map)
        JSONAssert.assertEquals(
            """
            {
                "00000000000000000000000000000000": "coucou"
            }
            """,
            json,
            true)
    }
}
