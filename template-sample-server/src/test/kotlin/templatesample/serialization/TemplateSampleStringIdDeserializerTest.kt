package templatesample.serialization

import templatesample.domain.TestIds.sampleStringId
import templatesample.domain.TestStringId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleStringIdDeserializerTest {

    @Test
    fun `test DeviceId deserialization`() {
        assertEquals(
            TestStringId(sampleStringId),
            Serializer.deserialize<TestStringId>("\"$sampleStringId\""))
    }
}
