package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import templatesample.domain.TemplateSampleStringId

class TemplateSampleStringIdKeySerializer :
    StdSerializer<TemplateSampleStringId>(TemplateSampleStringId::class.java) {

    override fun serialize(
        value: TemplateSampleStringId,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeFieldName(TemplateSampleStringIdSerializer.serialize(value))
}
