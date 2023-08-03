package templatesample.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import templatesample.domain.TemplateSampleSecurityString
import kotlin.reflect.KClass

class TemplateSampleSecurityStringKeyDeserializer<T : TemplateSampleSecurityString>(
    val TemplateSampleSecurityString: KClass<T>
) : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext?) =
        TemplateSampleSecurityStringDeserializer.deserialize(TemplateSampleSecurityString, key)
}
