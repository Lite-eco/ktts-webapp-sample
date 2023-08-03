package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.kttswebapptemplate.domain.TemplateSampleUuidId

class TemplateSampleUuidIdSerializer :
    StdSerializer<TemplateSampleUuidId>(TemplateSampleUuidId::class.java) {

    companion object {
        fun serialize(value: TemplateSampleUuidId) =
            TemplateSampleSerializationPrefixUtils.prefix(value) + value.stringUuid()
    }

    override fun serialize(
        value: TemplateSampleUuidId,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeString(serialize(value))
}
