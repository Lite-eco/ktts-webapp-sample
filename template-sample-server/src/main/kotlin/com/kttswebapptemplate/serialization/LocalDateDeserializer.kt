package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.kttswebapptemplate.error.TemplateSampleSerializationLocalDateException
import java.time.LocalDate
import java.time.format.DateTimeParseException

class LocalDateDeserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDate =
        try {
            LocalDate.parse(p.valueAsString, LocalDateSerializer.formatter)
        } catch (e: DateTimeParseException) {
            throw TemplateSampleSerializationLocalDateException(p.valueAsString)
        }
}
