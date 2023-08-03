package com.kttswebapptemplate.controller

import com.kttswebapptemplate.command.AdminUpdateRolesCommand
import com.kttswebapptemplate.command.AdminUpdateRolesCommandHandler
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
import com.kttswebapptemplate.command.UpdatePasswordCommand
import com.kttswebapptemplate.command.UpdatePasswordCommandHandler
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.repository.log.CommandLogDao
import com.kttswebapptemplate.serialization.Serializer
import com.kttswebapptemplate.service.user.UserSessionService
import com.kttswebapptemplate.service.utils.ApplicationInstance
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.TransactionIsolationService
import com.kttswebapptemplate.service.utils.random.IdLogService
import com.kttswebapptemplate.service.utils.random.RandomService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.Instant
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CommandController(
    private val commandLogDao: CommandLogDao,
    private val dateService: DateService,
    private val idLogService: IdLogService,
    private val randomService: RandomService,
    private val transactionIsolationService: TransactionIsolationService,
    private val userSessionService: UserSessionService,
    private val adminUpdateRolesCommandHandler: AdminUpdateRolesCommandHandler,
    private val devLoginCommandHandler: DevLoginCommandHandler,
    private val loginCommandHandler: LoginCommandHandler,
    private val registerCommandHandler: RegisterCommandHandler,
    private val updatePasswordCommandHandler: UpdatePasswordCommandHandler,
) {

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
        val draftCommandLog =
            CommandLogDao.Record(
                id = randomService.id(),
                userId = null, // set later
                affectedUserId = affectedUserId(command),
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
                draftCommandLog.copy(
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
                draftCommandLog.copy(
                    userId = updatedSession?.userId,
                    userSessionId = updatedSession?.sessionId,
                    idsLog = idLogService.getIdsString(),
                    exceptionStackTrace = ExceptionUtils.getStackTrace(e),
                    endDate = dateService.now()))
            throw e
        } finally {
            idLogService.clean()
            // [doc] CSRF token is updated by XorCsrfTokenRequestAttributeHandler.handle()
            // this code permits to update the token in the front
            (request.getAttribute("_csrf") as? CsrfToken)?.let {
                response.setHeader(it.headerName, it.token)
            }
                ?: throw RuntimeException()
        }
    }

    private fun getSession() =
        if (userSessionService.isAuthenticated()) userSessionService.getUserSession() else null

    private fun handler(command: Command) =
        when (command) {
            is AdminUpdateRolesCommand -> adminUpdateRolesCommandHandler
            is DevLoginCommand -> devLoginCommandHandler
            is LoginCommand -> loginCommandHandler
            is RegisterCommand -> registerCommandHandler
            is UpdatePasswordCommand -> updatePasswordCommandHandler
        }.let { @Suppress("UNCHECKED_CAST") (it as CommandHandler<Command, CommandResponse>) }

    // for admin commands, should return the affected user when there's one
    private fun affectedUserId(command: Command): UserId? =
        when (command) {
            is DevLoginCommand,
            is LoginCommand,
            is RegisterCommand,
            is UpdatePasswordCommand -> null
            is AdminUpdateRolesCommand -> command.userId
        }
}
