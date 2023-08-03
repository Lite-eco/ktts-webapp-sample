package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.util.UUID
import templatesample.utils.TemplateSampleStringUtils

class UuidKeySerializer : StdSerializer<UUID>(UUID::class.java) {

    override fun serialize(value: UUID, gen: JsonGenerator, provider: SerializerProvider) =
        gen.writeFieldName(TemplateSampleStringUtils.serializeUuid(value))
}
