package com.kttswebapptemplate.domain

data class TestSecurityString(override val rawString: String) :
    TemplateSampleSecurityString(rawString) {
    override fun length() = TestStringId.length
}

@Prefix("test_prefix")
data class TestPrefixSecurityString(override val rawString: String) :
    TemplateSampleSecurityString(rawString) {
    override fun length() = TestStringId.length
}
