package templatesample.serialization

import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import templatesample.domain.TestIds.emptyUuid0
import templatesample.domain.TestUuidId
import templatesample.utils.toTypeId

internal class TemplateSampleUuidIdKeySerializerTest {

    @Test
    fun testSerialization() {
        val map = mapOf(emptyUuid0.toTypeId<TestUuidId>() to "coucou")
        val json = Serializer.serialize(map)
        JSONAssert.assertEquals(
            """
            {
                "00000000000000000000000000000000": "coucou"
            }
            """,
            json,
            true)
    }
}
