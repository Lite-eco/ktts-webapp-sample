package com.kttswebapptemplate.domain

import com.kttswebapptemplate.utils.toTypeId
import com.kttswebapptemplate.utils.uuid
import java.util.UUID

object TestIds {
    fun sampleUUID(endsWith: Int = 0) = endsWith.toString().padStart(32, '0').uuid()

    inline fun <reified R : TemplateSampleUuidId> sampleNodesUuidId(endsWith: Int = 0): R =
        sampleUUID(endsWith).toTypeId<R>()

    fun sampleString(endWith: String = "") = "sample-string-id - $endWith"

    inline fun <reified R : TemplateSampleStringId> sampleNodesStringId(endsWith: String = ""): R =
        sampleString(endsWith).toTypeId<R>()
}

data class TestUuidId(override val rawId: UUID) : TemplateSampleUuidId()

@Prefix("test_prefix")
data class TestPrefixUuidId(override val rawId: UUID) : TemplateSampleUuidId()

data class TestStringId(override val rawId: String) : TemplateSampleStringId(rawId) {
    companion object {
        val length = 20

        fun sample() = TestStringId("sample".padStart(length))
    }

    override fun length() = length
}

@Prefix("test_prefix")
data class TestPrefixStringId(override val rawId: String) : TemplateSampleStringId(rawId) {
    override fun length() = TestStringId.length
}
