package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import templatesample.domain.SerializeAsString

class SerializeAsStringSerializer :
    StdSerializer<SerializeAsString>(SerializeAsString::class.java) {

    companion object {
        fun serialize(value: SerializeAsString) = value.value
    }

    override fun serialize(
        value: SerializeAsString,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeString(Companion.serialize(value))
}
