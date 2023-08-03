package templatesample.controller

import templatesample.command.Command
import templatesample.command.CommandConfiguration
import templatesample.command.CommandHandler
import templatesample.command.CommandResponse
import templatesample.command.DevLoginCommand
import templatesample.command.DevLoginCommandHandler
import templatesample.command.DevLoginCommandResponse
import templatesample.command.EmptyCommandResponse
import templatesample.command.LoginCommand
import templatesample.command.LoginCommandHandler
import templatesample.command.LoginCommandResponse
import templatesample.command.RegisterCommand
import templatesample.command.RegisterCommandHandler
import templatesample.command.RegisterCommandResponse
import templatesample.domain.CommandLogId
import templatesample.repository.log.CommandLogDao
import templatesample.repository.user.UserDao
import templatesample.serialization.Serializer
import templatesample.service.ApplicationInstance
import templatesample.service.DateService
import templatesample.service.IdLogService
import templatesample.service.RandomService
import templatesample.service.user.UserSessionService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.transaction.PlatformTransactionManager
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
    val transactionManager: PlatformTransactionManager,
    val idLogService: IdLogService,
    val userSessionService: UserSessionService,
    val devLoginCommandHandler: DevLoginCommandHandler,
    val loginCommandHandler: LoginCommandHandler,
    val registerCommandHandler: RegisterCommandHandler,
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
        val userSession =
            if (userSessionService.isAuthenticated()) userSessionService.getUserSession() else null
        // [doc] is filtered from sensitive data (passwords) because of serializers
        val filteredJsonCommand = Serializer.serialize(command)
        // TODO[tmpl][test] make an only insert at the end, with no update ?
        // or make an insert first to protect from some errors (? isolate its transactionl)
        val commandLogId = randomService.id<CommandLogId>()
        commandLogDao.insert(
            CommandLogDao.Record(
                id = commandLogId,
                userId = userSession?.userId,
                deploymentLogId = applicationInstance.deploymentId,
                commandClass = command.javaClass,
                jsonCommand = filteredJsonCommand,
                // the ip can evolve within a session, it makes sense to save it here
                ip = request.remoteAddr,
                userSessionId = userSession?.sessionId,
                resultingIds = null,
                jsonResult = null,
                exceptionStackTrace = null,
                startDate = dateService.now(),
                endDate = null))
        val transaction = transactionManager.getTransaction(null)

        try {
            userSessionService.verifyRoleOrFail(
                CommandConfiguration.role(command), request.remoteAddr, command.javaClass)
            idLogService.enableLogging()
            val result = handler.handle(command, userSession, request, response)
            transactionManager.commit(transaction)
            try {
                commandLogDao.updateResult(
                    commandLogId,
                    // TODO[tmpl] verify
                    when (result) {
                        is DevLoginCommandResponse -> result.userinfos.id
                        is LoginCommandResponse -> result.userinfos?.id
                        is RegisterCommandResponse -> result.userinfos?.id
                        else -> null
                    }?.let { it to userSessionService.getUserSession().sessionId },
                    idLogService.getIdsString(),
                    if (result !is EmptyCommandResponse) Serializer.serialize(result) else null,
                    dateService.now())
            } catch (e: Exception) {
                logger.error(e) { "Exception in command result log..." }
            }
            return result
        } catch (e: Exception) {
            transactionManager.rollback(transaction)
            try {
                commandLogDao.updateExceptionStackTrace(
                    commandLogId, ExceptionUtils.getStackTrace(e), dateService.now())
            } catch (e2: Exception) {
                logger.error(e2) { "Exception in command exception log..." }
            }
            throw e
        } finally {
            idLogService.clean()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handler(command: Command) =
        when (command) {
            is DevLoginCommand -> devLoginCommandHandler
            is LoginCommand -> loginCommandHandler
            is RegisterCommand -> registerCommandHandler
        }
            as CommandHandler<Command, CommandResponse>
}
