package com.kttswebapptemplate.serialization

import com.kttswebapptemplate.domain.Prefix
import com.kttswebapptemplate.domain.TemplateSampleId
import com.kttswebapptemplate.domain.TemplateSampleSecurityString
import kotlin.reflect.KClass

object TemplateSampleSerializationPrefixUtils {

    fun prefix(value: TemplateSampleId<*>) = extractPrefix(value::class)

    fun prefix(value: TemplateSampleSecurityString) = extractPrefix(value::class)

    private fun extractPrefix(itemClass: KClass<*>) =
        itemClass.annotations.filterIsInstance<Prefix>().firstOrNull()?.value?.let { it + "_" }
            ?: ""

    fun removePrefix(itemClass: KClass<*>, value: String) =
        itemClass.annotations.filterIsInstance<Prefix>().firstOrNull()?.value?.let { prefix ->
            if (!value.startsWith(prefix)) {
                throw IllegalArgumentException("Missing id prefix $prefix on $itemClass: $value")
            }
            value.substring(prefix.length + 1)
        } ?: value
}
