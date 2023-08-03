package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.kttswebapptemplate.domain.TemplateSampleUuidId
import com.kttswebapptemplate.utils.uuid
import kotlin.reflect.KClass

class TemplateSampleUuidIdDeserializer<T : TemplateSampleUuidId>(
    val templateSampleUuidIdClass: KClass<T>
) : StdDeserializer<T>(templateSampleUuidIdClass.java) {

    companion object {
        fun <T : TemplateSampleUuidId> deserialize(
            templateSampleUuidIdClass: KClass<T>,
            value: String
        ): T =
            TemplateSampleSerializationPrefixUtils.removePrefix(templateSampleUuidIdClass, value)
                .let { templateSampleUuidIdClass.constructors.first().call(it.uuid()) }
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T =
        deserialize(templateSampleUuidIdClass, p.valueAsString)
}
