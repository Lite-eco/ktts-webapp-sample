package com.kttswebapptemplate.controller

import com.kttswebapptemplate.controller.IndexController.Companion.extractDomain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IndexControllerCompanionTest() {

    @Test
    fun `test url domain extraction`() {
        assertEquals("localhost", extractDomain("http://localhost:3123"))
        assertEquals("localhost", extractDomain("http://localhost:3123/"))
        assertEquals("localhost", extractDomain("http://localhost:3123/djvodsjv"))
        assertEquals("localhost", extractDomain("http://localhost:3123/djvodsjv/dsvsdvds"))
        assertEquals("localhost", extractDomain("https://localhost:3123"))
        assertEquals("localhost", extractDomain("https://localhost:3123/"))
        assertEquals("localhost", extractDomain("https://localhost:3123/djvodsjv"))
        assertEquals("localhost", extractDomain("https://localhost:3123/djvodsjv/dsvsdvds"))
    }
}
