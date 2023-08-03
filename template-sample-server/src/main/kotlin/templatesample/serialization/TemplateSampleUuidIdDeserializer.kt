package templatesample.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import kotlin.reflect.KClass
import templatesample.domain.TemplateSampleUuidId
import templatesample.utils.TemplateSampleStringUtils

class TemplateSampleUuidIdDeserializer<T : TemplateSampleUuidId>(
    val templateSampleUuidIdClass: KClass<T>
) : StdDeserializer<T>(templateSampleUuidIdClass.java) {

    companion object {
        fun <T : TemplateSampleUuidId> deserialize(
            templateSampleUuidIdClass: KClass<T>,
            value: String
        ): T =
            TemplateSampleSerializationPrefixUtils.removePrefix(templateSampleUuidIdClass, value)
                .let { TemplateSampleStringUtils.deserializeUuid(it) }
                .let { templateSampleUuidIdClass.constructors.first().call(it) }
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T =
        deserialize(templateSampleUuidIdClass, p.valueAsString)
}
