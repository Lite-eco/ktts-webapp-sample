package templatesample.serialization

import templatesample.domain.TestIds.emptyUuid0
import templatesample.domain.TestUuidId
import templatesample.utils.toTypeId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
