package com.kttswebapptemplate.controller

import com.kttswebapptemplate.command.Command
import com.kttswebapptemplate.command.CommandConfiguration
import com.kttswebapptemplate.command.CommandHandler
import com.kttswebapptemplate.command.CommandResponse
import com.kttswebapptemplate.command.DevLoginCommand
import com.kttswebapptemplate.command.DevLoginCommandHandler
import com.kttswebapptemplate.command.EmptyCommandResponse
import com.kttswebapptemplate.command.LoginCommand
import com.kttswebapptemplate.command.LoginCommandHandler
import com.kttswebapptemplate.command.RegisterCommand
import com.kttswebapptemplate.command.RegisterCommandHandler
import com.kttswebapptemplate.repository.log.CommandLogDao
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.serialization.Serializer
import com.kttswebapptemplate.service.user.UserSessionService
import com.kttswebapptemplate.service.utils.ApplicationInstance
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.TransactionIsolationService
import com.kttswebapptemplate.service.utils.random.IdLogService
import com.kttswebapptemplate.service.utils.random.RandomService
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
    private val commandLogDao: CommandLogDao,
    private val userDao: UserDao,
    private val dateService: DateService,
    private val randomService: RandomService,
    private val idLogService: IdLogService,
    private val userSessionService: UserSessionService,
    private val devLoginCommandHandler: DevLoginCommandHandler,
    private val loginCommandHandler: LoginCommandHandler,
    private val registerCommandHandler: RegisterCommandHandler,
    private val transactionIsolationService: TransactionIsolationService,
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

        // [doc] is filtered from sensitive data (passwords) thanks to serialization
        val filteredJsonCommand = Serializer.serialize(command)
        // TODO[tmpl][test] make an only insert at the end, with no update ?
        // or make an insert first to protect from some errors (? isolate its transactionl)
        val commandLog =
            CommandLogDao.Record(
                id = randomService.id(),
                userId = null,
                deploymentLogId = ApplicationInstance.deploymentLogId,
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