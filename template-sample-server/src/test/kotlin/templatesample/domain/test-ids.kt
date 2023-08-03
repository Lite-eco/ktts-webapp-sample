package templatesample.domain

import java.util.UUID

object TestIds {
    val emptyUuid0 = UUID.fromString("00000000-0000-0000-0000-000000000000")

    val sampleStringId = "my-string-id -------"
}

data class TestUuidId(override val rawId: UUID) : TemplateSampleUuidId()

@Prefix("test_prefix") data class TestPrefixUuidId(override val rawId: UUID) : TemplateSampleUuidId()

data class TestStringId(override val rawId: String) : TemplateSampleStringId(rawId) {
    companion object {
        val length = 20
    }

    override fun length() = length
}

@Prefix("test_prefix")
data class TestPrefixStringId(override val rawId: String) : TemplateSampleStringId(rawId) {
    override fun length() = TestStringId.length
}
