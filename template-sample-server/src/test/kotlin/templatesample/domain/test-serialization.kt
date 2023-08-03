package templatesample.domain

data class TestSerializeAsString(override val value: String) : SerializeAsString(value)
