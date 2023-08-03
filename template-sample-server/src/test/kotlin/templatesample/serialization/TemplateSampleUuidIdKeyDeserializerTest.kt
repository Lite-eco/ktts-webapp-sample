package templatesample.serialization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import templatesample.domain.TestIds.emptyUuid0
import templatesample.domain.TestUuidId
import templatesample.utils.toTypeId

internal class TemplateSampleUuidIdKeyDeserializerTest {

    @Test
    fun testDeserialization() {
        val json =
            """
            {
                "00000000000000000000000000000000": "coucou"
            }
            """
        val map = Serializer.deserialize<Map<TestUuidId, String>>(json)
        val expectedMap = mapOf(emptyUuid0.toTypeId<TestUuidId>() to "coucou")
        assertEquals(expectedMap, map)
    }
}
