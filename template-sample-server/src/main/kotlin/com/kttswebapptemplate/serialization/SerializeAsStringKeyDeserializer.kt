package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.kttswebapptemplate.domain.SerializeAsString
import kotlin.reflect.KClass

class SerializeAsStringKeyDeserializer<T : SerializeAsString>(
    private val templateSampleStringId: KClass<T>
) : KeyDeserializer() {

    override fun deserializeKey(key: String?, ctxt: DeserializationContext?): Any =
        // FIXME[tmpl] check about != null
        key?.let { SerializeAsStringDeserializer.deserialize(templateSampleStringId, it) }
            ?: throw IllegalArgumentException()
}
