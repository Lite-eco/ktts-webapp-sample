package templatesample.controller

import templatesample.controller.IndexController.Companion.extractDomain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IndexControllerCompanionTest() {

    @Test
    fun `test url domain extraction`() {
        assertEquals("http://localhost", extractDomain("http://localhost:3123"))
        assertEquals("http://localhost", extractDomain("http://localhost:3123/"))
        assertEquals("http://localhost", extractDomain("http://localhost:3123/djvodsjv"))
        assertEquals("http://localhost", extractDomain("http://localhost:3123/djvodsjv/dsvsdvds"))
        assertEquals("https://localhost", extractDomain("https://localhost:3123"))
        assertEquals("https://localhost", extractDomain("https://localhost:3123/"))
        assertEquals("https://localhost", extractDomain("https://localhost:3123/djvodsjv"))
        assertEquals("https://localhost", extractDomain("https://localhost:3123/djvodsjv/dsvsdvds"))
    }
}
