package templatesample.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import templatesample.utils.TemplateSampleStringUtils
import java.util.UUID

class UuidDeserializer : StdDeserializer<UUID>(UUID::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext) =
        TemplateSampleStringUtils.deserializeUuid(p.valueAsString)
}
