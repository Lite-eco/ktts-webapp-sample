package templatesample.serialization

import templatesample.domain.TestIds.emptyUuid0
import templatesample.domain.TestUuidId
import templatesample.utils.toTypeId
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

internal class TemplateSampleUuidIdSerializerTest {

    @Test
    fun `test TemplateSampleUuidId serialization`() {
        JSONAssert.assertEquals(
            "\"00000000000000000000000000000000\"",
            Serializer.serialize(emptyUuid0.toTypeId<TestUuidId>()),
            true)
    }
}
