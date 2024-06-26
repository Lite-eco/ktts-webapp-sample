package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.kttswebapptemplate.domain.TemplateSampleSecurityString
import kotlin.reflect.KClass

class TemplateSampleSecurityStringDeserializer<T : TemplateSampleSecurityString>(
    val TemplateSampleSecurityStringClass: KClass<T>
) : StdDeserializer<T>(TemplateSampleSecurityStringClass.java) {

    companion object {
        fun <T : TemplateSampleSecurityString> deserialize(
            TemplateSampleSecurityStringClass: KClass<T>,
            value: String
        ): T =
            TemplateSampleSerializationPrefixUtils.removePrefix(
                    TemplateSampleSecurityStringClass, value)
                .let { TemplateSampleSecurityStringClass.constructors.first().call(it) }
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T =
        deserialize(TemplateSampleSecurityStringClass, p.valueAsString)
}
