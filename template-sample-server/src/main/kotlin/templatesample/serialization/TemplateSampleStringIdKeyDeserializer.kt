package templatesample.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import kotlin.reflect.KClass
import templatesample.domain.TemplateSampleStringId

class TemplateSampleStringIdKeyDeserializer<T : TemplateSampleStringId>(
    val templateSampleStringId: KClass<T>
) : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext?) =
        TemplateSampleStringIdDeserializer.deserialize(templateSampleStringId, key)
}
