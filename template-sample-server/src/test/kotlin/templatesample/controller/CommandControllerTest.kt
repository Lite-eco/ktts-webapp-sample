package templatesample.controller

import templatesample.command.Command
import templatesample.command.LoginCommand
import templatesample.serialization.Serializer
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
