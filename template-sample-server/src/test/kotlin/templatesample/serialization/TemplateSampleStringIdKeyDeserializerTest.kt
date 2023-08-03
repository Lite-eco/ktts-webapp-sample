package templatesample.serialization

import templatesample.domain.TestIds.sampleStringId
import templatesample.domain.TestStringId
import templatesample.utils.toTypeId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
