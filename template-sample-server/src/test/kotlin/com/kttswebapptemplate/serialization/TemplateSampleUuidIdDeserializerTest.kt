package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestIds.emptyUuid0
import com.kttswebapptemplate.domain.TestUuidId
import com.kttswebapptemplate.utils.toTypeId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleUuidIdDeserializerTest {

    @Test
    fun `test TemplateSampleUuidId deserialization`() {
        assertEquals(
            emptyUuid0.toTypeId<TestUuidId>(),
            Serializer.deserialize<TestUuidId>("\"00000000000000000000000000000000\""))
    }
}
