package templatesample.serialization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import templatesample.domain.TestIds.sampleStringId
import templatesample.domain.TestStringId

internal class TemplateSampleStringIdDeserializerTest {

    @Test
    fun `test TemplateSampleStringId deserialization`() {
        assertEquals(
            TestStringId(sampleStringId),
            Serializer.deserialize<TestStringId>("\"$sampleStringId\""))
    }
}
