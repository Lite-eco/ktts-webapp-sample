package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestIds.sampleStringId
import com.kttswebapptemplate.domain.TestStringId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleStringIdDeserializerTest {

    @Test
    fun `test TemplateSampleStringId deserialization`() {
        assertEquals(
            TestStringId(sampleStringId),
            Serializer.deserialize<TestStringId>("\"$sampleStringId\""))
    }
}
