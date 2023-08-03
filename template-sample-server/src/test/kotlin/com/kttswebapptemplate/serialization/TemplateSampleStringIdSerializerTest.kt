package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.TestIds.sampleStringId
import com.kttswebapptemplate.domain.TestStringId
import com.kttswebapptemplate.utils.toTypeId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleStringIdSerializerTest {

    @Test
    fun `test TemplateSampleStringId serialization`() {
        val id = sampleStringId.toTypeId<TestStringId>()
        assertEquals("\"$sampleStringId\"", Serializer.serialize(id))
    }
}
