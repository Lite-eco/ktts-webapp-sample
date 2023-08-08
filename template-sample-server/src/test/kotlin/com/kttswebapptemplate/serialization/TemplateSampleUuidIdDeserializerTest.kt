package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestIds.sampleNodesUuidId
import com.kttswebapptemplate.domain.TestUuidId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleUuidIdDeserializerTest {

    @Test
    fun `test TemplateSampleUuidId deserialization`() {
        assertEquals(
            sampleNodesUuidId<TestUuidId>(),
            Serializer.deserialize<TestUuidId>("\"00000000000000000000000000000000\""))
    }
}
