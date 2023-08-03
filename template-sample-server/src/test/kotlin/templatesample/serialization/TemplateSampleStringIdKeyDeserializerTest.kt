package templatesample.serialization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import templatesample.domain.TestIds.sampleStringId
import templatesample.domain.TestStringId
import templatesample.utils.toTypeId

internal class TemplateSampleStringIdKeyDeserializerTest {

    @Test
    fun testDeserialization() {
        val json =
            """
            {
                "$sampleStringId": "coucou"
            }
            """
        val map = Serializer.deserialize<Map<TestStringId, String>>(json)
        val expectedMap = mapOf(sampleStringId.toTypeId<TestStringId>() to "coucou")
        assertEquals(expectedMap, map)
    }
}
