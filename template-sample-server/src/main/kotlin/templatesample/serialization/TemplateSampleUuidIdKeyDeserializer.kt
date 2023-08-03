package templatesample.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import kotlin.reflect.KClass
import templatesample.domain.TemplateSampleUuidId

class TemplateSampleUuidIdKeyDeserializer<T : TemplateSampleUuidId>(
    val templateSampleUuidIdClass: KClass<T>
) : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext?) =
        TemplateSampleUuidIdDeserializer.deserialize(templateSampleUuidIdClass, key)
}
