package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.kttswebapptemplate.utils.TemplateSampleStringUtils
import java.util.UUID

class UuidKeySerializer : StdSerializer<UUID>(UUID::class.java) {

    override fun serialize(value: UUID, gen: JsonGenerator, provider: SerializerProvider) =
        gen.writeFieldName(TemplateSampleStringUtils.serializeUuid(value))
}
