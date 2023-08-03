package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.kttswebapptemplate.utils.TemplateSampleStringUtils
import java.util.UUID

class UuidDeserializer : StdDeserializer<UUID>(UUID::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext) =
        TemplateSampleStringUtils.deserializeUuid(p.valueAsString)
}
