package com.kttswebapptemplate.service.mail

import com.kttswebapptemplate.service.mail.MailService.Companion.extractMailPrefixSuffix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MailServiceCompanionTest {
    @Test
    fun `check extractMailPrefixSuffix()`() {
        assertEquals("" to "", extractMailPrefixSuffix(""))
        assertEquals("roger" to "", extractMailPrefixSuffix("roger"))
        assertEquals("roger" to "", extractMailPrefixSuffix("roger+test"))
        assertEquals("roger" to "gmail.com", extractMailPrefixSuffix("roger@gmail.com"))
        assertEquals("roger" to "gmail.com", extractMailPrefixSuffix("roger+test@gmail.com"))
        assertEquals("roger" to "gmail+test.com", extractMailPrefixSuffix("roger@gmail+test.com"))
        assertEquals(
            "roger" to "gmail+test.com", extractMailPrefixSuffix("roger+test@gmail+test.com"))
    }
}
