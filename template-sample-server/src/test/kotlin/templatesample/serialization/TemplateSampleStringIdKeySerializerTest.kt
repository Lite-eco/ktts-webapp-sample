package templatesample.serialization

import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import templatesample.domain.TestIds.sampleStringId
import templatesample.domain.TestStringId

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
