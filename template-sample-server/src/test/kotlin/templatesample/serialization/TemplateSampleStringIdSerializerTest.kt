package templatesample.serialization

import templatesample.domain.TestIds.sampleStringId
import templatesample.domain.TestStringId
import templatesample.utils.toTypeId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleStringIdSerializerTest {

    @Test
    fun `test TemplateSampleStringId serialization`() {
        val id = sampleStringId.toTypeId<TestStringId>()
        assertEquals("\"$sampleStringId\"", Serializer.serialize(id))
    }
}
