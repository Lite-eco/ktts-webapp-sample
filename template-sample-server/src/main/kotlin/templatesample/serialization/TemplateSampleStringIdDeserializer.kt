package templatesample.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import templatesample.domain.TemplateSampleStringId
import kotlin.reflect.KClass

class TemplateSampleStringIdDeserializer<T : TemplateSampleStringId>(val templateSampleStringIdClass: KClass<T>) :
    StdDeserializer<T>(templateSampleStringIdClass.java) {

    companion object {
        fun <T : TemplateSampleStringId> deserialize(templateSampleStringIdClass: KClass<T>, value: String): T =
            TemplateSampleSerializationPrefixUtils.removePrefix(templateSampleStringIdClass, value).let {
                templateSampleStringIdClass.constructors.first().call(it)
            }
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T =
        deserialize(templateSampleStringIdClass, p.valueAsString)
}
