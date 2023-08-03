package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.kttswebapptemplate.domain.TemplateSampleStringId

class TemplateSampleStringIdSerializer :
    StdSerializer<TemplateSampleStringId>(TemplateSampleStringId::class.java) {

    companion object {
        fun serialize(value: TemplateSampleStringId) =
            TemplateSampleSerializationPrefixUtils.prefix(value) + value.rawId
    }

    override fun serialize(
        value: TemplateSampleStringId,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeString(serialize(value))
}
