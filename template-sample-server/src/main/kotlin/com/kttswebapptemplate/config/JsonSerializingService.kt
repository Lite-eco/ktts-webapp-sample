package com.kttswebapptemplate.config

import com.kttswebapptemplate.domain.Session
import com.kttswebapptemplate.serialization.Serializer.serialize
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextImpl

class JsonSerializingService : Converter<Any, ByteArray> {

    override fun convert(source: Any) =
        if (source is SecurityContextImpl &&
            source.authentication is UsernamePasswordAuthenticationToken) {
            if (source.authentication.principal is Session) {
                serialize(source.authentication.principal).toByteArray()
            } else {
                throw IllegalArgumentException(
                    "Unexpected session: ${source.authentication.principal}")
            }
        } else {
            throw IllegalArgumentException("Unexpected session: $source")
        }
}
