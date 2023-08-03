package templatesample.serialization

import templatesample.domain.TestIds.emptyUuid0
import templatesample.domain.TestUuidId
import templatesample.utils.toTypeId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TemplateSampleUuidIdDeserializerTest {

    @Test
    fun `test DeviceId deserialization`() {
        assertEquals(
            emptyUuid0.toTypeId<TestUuidId>(),
            Serializer.deserialize<TestUuidId>("\"00000000000000000000000000000000\""))
    }
}
