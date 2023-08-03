package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestIds.sampleStringId
import com.kttswebapptemplate.domain.TestStringId
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

internal class TemplateSampleStringIdKeySerializerTest {

    @Test
    fun testSerialization() {
        val map = mapOf(TestStringId(sampleStringId) to "coucou")
        val json = Serializer.serialize(map)
        JSONAssert.assertEquals(
            """
            {
                "$sampleStringId": "coucou"
            }
            """,
            json,
            true)
    }
}
