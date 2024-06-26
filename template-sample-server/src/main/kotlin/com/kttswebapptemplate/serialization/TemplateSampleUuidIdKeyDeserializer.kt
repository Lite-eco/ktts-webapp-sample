package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.kttswebapptemplate.domain.TemplateSampleUuidId
import kotlin.reflect.KClass

class TemplateSampleUuidIdKeyDeserializer<T : TemplateSampleUuidId>(
    val templateSampleUuidIdClass: KClass<T>
) : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext?) =
        TemplateSampleUuidIdDeserializer.deserialize(templateSampleUuidIdClass, key)
}
