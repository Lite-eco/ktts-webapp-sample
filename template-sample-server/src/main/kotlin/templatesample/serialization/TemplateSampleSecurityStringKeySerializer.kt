package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import templatesample.domain.TemplateSampleSecurityString

class TemplateSampleSecurityStringKeySerializer :
    StdSerializer<TemplateSampleSecurityString>(TemplateSampleSecurityString::class.java) {

    override fun serialize(
        value: TemplateSampleSecurityString,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeFieldName(TemplateSampleSecurityStringSerializer.serialize(value))
}
