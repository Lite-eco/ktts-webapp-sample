package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import templatesample.domain.SerializeAsString

class SerializeAsStringKeySerializer :
    StdSerializer<SerializeAsString>(SerializeAsString::class.java) {

    override fun serialize(
        value: SerializeAsString,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeFieldName(SerializeAsStringSerializer.serialize(value))
}
