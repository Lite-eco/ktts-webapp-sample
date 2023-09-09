package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.PlainStringPassword
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class RegisterCommandHandlerCompanionTest {

    val sampleRegisterCommand =
        RegisterCommand(
            mail = "  HÉL lo@mytest.net  ",
            password = PlainStringPassword("  password  "),
            displayName = "  display name  ")

    //    val sampleIdentityDto = IdentityDto("  HÉL lo@mytest.net  ", "  firstname  ", "  lastname
    // ",
    //            "012356789", null)

    @Test
    fun `validate validation ok`() {
        RegisterCommandHandler.validateRegisterCommand(sampleRegisterCommand)
    }

    @Test
    fun `validate mail validation`() {
        val exceptionThatWasThrown =
            assertThrows(IllegalArgumentException::class.java) {
                RegisterCommandHandler.validateRegisterCommand(
                    sampleRegisterCommand.copy(mail = " "))
            }
        assertEquals("Mail is blank", exceptionThatWasThrown.message)
    }

    @Test
    fun `validate password validation`() {
        val exceptionThatWasThrown =
            assertThrows(IllegalArgumentException::class.java) {
                RegisterCommandHandler.validateRegisterCommand(
                    sampleRegisterCommand.copy(password = PlainStringPassword(" ")))
            }
        assertEquals("Password is blank", exceptionThatWasThrown.message)
    }

    @Test
    fun `validate displayName validation`() {
        val exceptionThatWasThrown =
            assertThrows(IllegalArgumentException::class.java) {
                RegisterCommandHandler.validateRegisterCommand(
                    sampleRegisterCommand.copy(displayName = " "))
            }
        assertEquals("Display name is blank", exceptionThatWasThrown.message)
    }
}
