package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import templatesample.domain.TemplateSampleUuidId

class TemplateSampleUuidIdKeySerializer : StdSerializer<TemplateSampleUuidId>(TemplateSampleUuidId::class.java) {

    override fun serialize(value: TemplateSampleUuidId, gen: JsonGenerator, provider: SerializerProvider) =
        gen.writeFieldName(TemplateSampleUuidIdSerializer.serialize(value))
}
