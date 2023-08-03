package templatesample.serialization

import templatesample.domain.TestIds.sampleStringId
import templatesample.domain.TestStringId
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

internal class TemplateSampleStringIdKeySerializerTest {

    @Test
    fun testSerialization() {
        val map = mapOf(TestStringId(sampleStringId) to "coucou")
        val json = Serializer.serialize(map)
        JSONAssert.assertEquals(
            """
            {
                "$sampleStringId": "coucou"
            }
            """,
            json,
            true)
    }
}
