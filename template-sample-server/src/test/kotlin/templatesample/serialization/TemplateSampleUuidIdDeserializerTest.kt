package templatesample.serialization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import templatesample.domain.TestIds.emptyUuid0
import templatesample.domain.TestUuidId
import templatesample.utils.toTypeId

internal class TemplateSampleUuidIdDeserializerTest {

    @Test
    fun `test TemplateSampleUuidId deserialization`() {
        assertEquals(
            emptyUuid0.toTypeId<TestUuidId>(),
            Serializer.deserialize<TestUuidId>("\"00000000000000000000000000000000\""))
    }
}
