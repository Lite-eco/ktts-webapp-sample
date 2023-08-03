package templatesample.serialization

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import templatesample.utils.TemplateSampleStringUtils

class UuidKeyDeserializer : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext?) =
        TemplateSampleStringUtils.deserializeUuid(key)
}
