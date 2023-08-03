package templatesample.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import kotlin.reflect.KClass
import templatesample.domain.SerializeAsString

class SerializeAsStringKeyDeserializer<T : SerializeAsString>(
    val templateSampleStringId: KClass<T>
) : KeyDeserializer() {

    override fun deserializeKey(key: String?, ctxt: DeserializationContext?): Any =
        // FIXME[tmpl] check about != null
        key?.let { SerializeAsStringDeserializer.deserialize(templateSampleStringId, it) }
            ?: throw IllegalArgumentException()
}
