package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestStringId
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

internal class TemplateSampleStringIdKeySerializerTest {

    @Test
    fun testSerialization() {
        val map = mapOf(TestStringId.sample() to "coucou")
        val json = Serializer.serialize(map)
        JSONAssert.assertEquals(
            """
            {
                "${TestStringId.sample().rawId}": "coucou"
            }
            """,
            json,
            true)
    }
}
