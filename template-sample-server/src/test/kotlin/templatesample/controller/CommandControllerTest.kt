package templatesample.controller

import org.junit.jupiter.api.Test
import templatesample.command.Command
import templatesample.command.LoginCommand
import templatesample.serialization.Serializer

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
