package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.kttswebapptemplate.domain.PlainStringPassword
import com.kttswebapptemplate.utils.TemplateSampleStringUtils.filteredPassword

class PlainStringPasswordSerializer :
    StdSerializer<PlainStringPassword>(PlainStringPassword::class.java) {

    override fun serialize(
        value: PlainStringPassword,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) = gen.writeString(filteredPassword)
}
