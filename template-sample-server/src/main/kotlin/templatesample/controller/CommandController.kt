package templatesample.controller

import templatesample.command.Command
import templatesample.command.CommandConfiguration
import templatesample.command.CommandHandler
import templatesample.command.CommandResponse
import templatesample.command.DevLoginCommand
import templatesample.command.DevLoginCommandHandler
import templatesample.command.EmptyCommandResponse
import templatesample.command.LoginCommand
import templatesample.command.LoginCommandHandler
import templatesample.command.RegisterCommand
import templatesample.command.RegisterCommandHandler
import templatesample.repository.log.CommandLogDao
import templatesample.repository.user.UserDao
import templatesample.serialization.Serializer
import templatesample.service.ApplicationInstance
import templatesample.service.DateService
import templatesample.service.IdLogService
import templatesample.service.RandomService
import templatesample.service.user.UserSessionService
import templatesample.service.utils.TransactionIsolationService
import java.time.Instant
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CommandController(
    val commandLogDao: CommandLogDao,
    val userDao: UserDao,
    val applicationInstance: ApplicationInstance,
    val dateService: DateService,
    val randomService: RandomService,
    val idLogService: IdLogService,
    val userSessionService: UserSessionService,
    val devLoginCommandHandler: DevLoginCommandHandler,
    val loginCommandHandler: LoginCommandHandler,
    val registerCommandHandler: RegisterCommandHandler,
    val transactionIsolationService: TransactionIsolationService,
) {

    private val logger = KotlinLogging.logger {}

    // TODO[tmpl][test] test these transactions & stacktrace logging !
    @PostMapping("/command")
    fun handle(
        @RequestBody jsonCommand: String,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): CommandResponse? {
        val command = Serializer.deserialize<Command>(jsonCommand)
        val handler = handler(command)

        // [doc] is filtered from sensitive data (passwords) because of serializers
        val filteredJsonCommand = Serializer.serialize(command)
        // TODO[tmpl][test] make an only insert at the end, with no update ?
        // or make an insert first to protect from some errors (? isolate its transactionl)
        val commandLog =
            CommandLogDao.Record(
                id = randomService.id(),
                userId = null,
                deploymentLogId = applicationInstance.deploymentId,
                commandClass = command.javaClass,
                jsonCommand = filteredJsonCommand,
                // the ip can evolve within a session, it makes sense to save it here
                ip = request.remoteAddr,
                userSessionId = null,
                idsLog = "",
                jsonResult = null,
                exceptionStackTrace = null,
                startDate = dateService.now(),
                endDate = Instant.ofEpochMilli(0))
        try {
            userSessionService.verifyRoleOrFail(
                CommandConfiguration.role(command), request.remoteAddr, command.javaClass)
            idLogService.enableLogging()
            val result =
                transactionIsolationService.execute {
                    handler.handle(command, getSession(), request, response)
                }
            // [doc] because of login, register...
            val updatedSession = getSession()
            commandLogDao.insert(
                commandLog.copy(
                    userId = updatedSession?.userId,
                    userSessionId = updatedSession?.sessionId,
                    idsLog = idLogService.getIdsString(),
                    jsonResult =
                        if (result !is EmptyCommandResponse) Serializer.serialize(result) else null,
                    endDate = dateService.now()))
            return result
        } catch (e: Exception) {
            val updatedSession = getSession()
            commandLogDao.insert(
                commandLog.copy(
                    userId = updatedSession?.userId,
                    userSessionId = updatedSession?.sessionId,
                    idsLog = idLogService.getIdsString(),
                    exceptionStackTrace = ExceptionUtils.getStackTrace(e),
                    endDate = dateService.now()))
            throw e
        } finally {
            idLogService.clean()
        }
    }

    private fun getSession() =
        if (userSessionService.isAuthenticated()) userSessionService.getUserSession() else null

    private fun handler(command: Command) =
        when (command) {
            is DevLoginCommand -> devLoginCommandHandler
            is LoginCommand -> loginCommandHandler
            is RegisterCommand -> registerCommandHandler
        }.let { @Suppress("UNCHECKED_CAST") (it as CommandHandler<Command, CommandResponse>) }
}
