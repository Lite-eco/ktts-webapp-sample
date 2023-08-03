package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.kttswebapptemplate.domain.TemplateSampleStringId
import kotlin.reflect.KClass

class TemplateSampleStringIdKeyDeserializer<T : TemplateSampleStringId>(
    private val templateSampleStringId: KClass<T>
) : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext?) =
        TemplateSampleStringIdDeserializer.deserialize(templateSampleStringId, key)
}
