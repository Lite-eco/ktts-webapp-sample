package com.kttswebapptemplate.domain

import com.kttswebapptemplate.utils.toSecurityString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TemplateSampleSecurityStringTest {

    data class TestTooShortSecurityString(override val rawString: String) :
        TemplateSampleSecurityString(rawString) {
        companion object {
            val length = 10
        }

        override fun length() = length
    }

    @Test
    fun `test minimum length security`() {
        val exceptionThatWasThrown =
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                "".padEnd(TestTooShortSecurityString.length, '-')
                    .toSecurityString<TestTooShortSecurityString>()
            }
        assertEquals(
            "TestTooShortSecurityString minimal length is 10, should not be less than 20",
            exceptionThatWasThrown.message)
    }

    @Test
    fun `test length is respected`() {
        val exceptionThatWasThrown =
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                "".padEnd(TestStringId.length - 1, '-').toSecurityString<TestSecurityString>()
            }
        assertEquals(
            "TestSecurityString length must be 20 (not 19)", exceptionThatWasThrown.message)
    }
}
