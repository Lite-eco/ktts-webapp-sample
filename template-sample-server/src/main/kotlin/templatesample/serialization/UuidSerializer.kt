package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import templatesample.utils.TemplateSampleStringUtils
import java.util.UUID

class UuidSerializer : StdSerializer<UUID>(UUID::class.java) {

    // TODO[tmpl][serialization] handle all the null cases ?
    override fun serialize(value: UUID?, gen: JsonGenerator, provider: SerializerProvider) =
        gen.writeString(value?.let { TemplateSampleStringUtils.serializeUuid(it) } ?: "null")
}
