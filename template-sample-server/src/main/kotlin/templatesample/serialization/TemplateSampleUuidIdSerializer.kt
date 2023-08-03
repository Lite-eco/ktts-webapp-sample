package templatesample.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import templatesample.domain.TemplateSampleUuidId
import templatesample.utils.TemplateSampleStringUtils

class TemplateSampleUuidIdSerializer :
    StdSerializer<TemplateSampleUuidId>(TemplateSampleUuidId::class.java) {

    companion object {
        fun serialize(value: TemplateSampleUuidId) =
            TemplateSampleSerializationPrefixUtils.prefix(value) +
                TemplateSampleStringUtils.serializeUuid(value.rawId)
    }

    override fun serialize(
        value: TemplateSampleUuidId,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeString(serialize(value))
}
