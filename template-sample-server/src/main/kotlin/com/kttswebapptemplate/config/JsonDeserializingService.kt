package com.kttswebapptemplate.config

import com.kttswebapptemplate.domain.Session
import com.kttswebapptemplate.serialization.Serializer
import java.util.Arrays
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextImpl

class JsonDeserializingService : Converter<ByteArray, Any> {

    // [doc] to read bytes log:
    // fun byteArrayOfInts(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }
    // println(String(byteArrayOfInts(123, 34, ...)))
    override fun convert(source: ByteArray): Any {
        val json =
            try {
                String(source)
            } catch (e: Exception) {
                throw IllegalArgumentException(
                    "Couldn't convert to string ${Arrays.toString(source)}", e)
            }
        return try {
            val session = Serializer.deserialize<Session>(json)
            SecurityContextImpl().apply {
                authentication = UsernamePasswordAuthenticationToken(session, null, null)
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("Couldn't deserialize session $json", e)
        }
    }
}
