package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import templatesample.domain.TemplateSampleSecurityString

class TemplateSampleSecurityStringSerializer :
    StdSerializer<TemplateSampleSecurityString>(TemplateSampleSecurityString::class.java) {

    companion object {
        fun serialize(value: TemplateSampleSecurityString) =
            TemplateSampleSerializationPrefixUtils.prefix(value) + value.rawString
    }

    override fun serialize(
        value: TemplateSampleSecurityString,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeString(serialize(value))
}
