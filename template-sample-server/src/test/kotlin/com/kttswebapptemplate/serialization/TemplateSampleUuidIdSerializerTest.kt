package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestIds.sampleNodesUuidId
import com.kttswebapptemplate.domain.TestUuidId
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

internal class TemplateSampleUuidIdSerializerTest {

    @Test
    fun `test TemplateSampleUuidId serialization`() {
        JSONAssert.assertEquals(
            "\"00000000000000000000000000000000\"",
            Serializer.serialize(sampleNodesUuidId<TestUuidId>()),
            true)
    }
}
