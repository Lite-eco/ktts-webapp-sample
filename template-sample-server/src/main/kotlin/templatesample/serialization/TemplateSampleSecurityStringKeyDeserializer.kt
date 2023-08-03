package templatesample.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import kotlin.reflect.KClass
import templatesample.domain.TemplateSampleSecurityString

class TemplateSampleSecurityStringKeyDeserializer<T : TemplateSampleSecurityString>(
    val TemplateSampleSecurityString: KClass<T>
) : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext?) =
        TemplateSampleSecurityStringDeserializer.deserialize(TemplateSampleSecurityString, key)
}
