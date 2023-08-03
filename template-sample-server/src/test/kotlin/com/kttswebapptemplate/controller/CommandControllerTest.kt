package com.kttswebapptemplate.controller

import com.kttswebapptemplate.command.Command
import com.kttswebapptemplate.command.LoginCommand
import com.kttswebapptemplate.serialization.Serializer
import org.junit.jupiter.api.Test

internal class CommandControllerTest {

    @Test
    fun command() {
        val json =
            """
        {
            "objectType":"LoginCommand",
            "mail": "username",
            "password": "password"
        }
        """
        val cmd = Serializer.deserialize<LoginCommand>(json)

        Serializer.deserialize<Command>(json)
    }
}
