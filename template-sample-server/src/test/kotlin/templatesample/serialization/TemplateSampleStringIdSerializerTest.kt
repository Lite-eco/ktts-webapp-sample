package templatesample.serialization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import templatesample.domain.TestIds.sampleStringId
import templatesample.domain.TestStringId
import templatesample.utils.toTypeId

internal class TemplateSampleStringIdSerializerTest {

    @Test
    fun `test TemplateSampleStringId serialization`() {
        val id = sampleStringId.toTypeId<TestStringId>()
        assertEquals("\"$sampleStringId\"", Serializer.serialize(id))
    }
}
