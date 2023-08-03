package templatesample.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import kotlin.reflect.KClass
import templatesample.domain.SerializeAsString

class SerializeAsStringDeserializer<T : SerializeAsString>(
    val templateSampleStringIdClass: KClass<T>
) : StdDeserializer<T>(templateSampleStringIdClass.java) {

    companion object {
        fun <T : SerializeAsString> deserialize(
            templateSampleStringIdClass: KClass<T>,
            value: String
        ) = templateSampleStringIdClass.constructors.first().call(value)
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T =
        deserialize(templateSampleStringIdClass, p.valueAsString)
}
