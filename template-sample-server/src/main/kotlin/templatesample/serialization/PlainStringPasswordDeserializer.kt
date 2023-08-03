package templatesample.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import templatesample.domain.PlainStringPassword

class PlainStringPasswordDeserializer :
    StdDeserializer<PlainStringPassword>(PlainStringPassword::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext) =
        PlainStringPassword(p.valueAsString)
}
