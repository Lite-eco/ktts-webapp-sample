package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.kttswebapptemplate.utils.uuid

class UuidKeyDeserializer : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext?) = key.uuid()
}
