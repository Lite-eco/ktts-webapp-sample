package templatesample.serialization

import templatesample.domain.TemplateSampleId
import templatesample.domain.TemplateSampleSecurityString
import templatesample.domain.Prefix
import kotlin.reflect.KClass

object TemplateSampleSerializationPrefixUtils {

    fun prefix(value: TemplateSampleId<*>) = extractPrefix(value::class)

    fun prefix(value: TemplateSampleSecurityString) = extractPrefix(value::class)

    private fun extractPrefix(itemClass: KClass<*>) =
        itemClass.annotations.mapNotNull { it as? Prefix }.firstOrNull()?.value?.let { it + "_" }
            ?: ""

    fun removePrefix(itemClass: KClass<*>, value: String) =
        itemClass.annotations
            .mapNotNull { it as? Prefix }
            .firstOrNull()
            ?.value
            ?.let { prefix ->
                if (!value.startsWith(prefix)) {
                    throw IllegalArgumentException(
                        "Missing id prefix $prefix on $itemClass : $value")
                }
                value.substring(prefix.length + 1)
            }
            ?: value
}
