package com.kttswebapptemplate.command

import com.kttswebapptemplate.domain.Language
import com.kttswebapptemplate.domain.UserSession
import com.kttswebapptemplate.domain.UserStatus
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.service.user.LostPasswordMailSenderService
import com.kttswebapptemplate.service.user.UserService
import com.kttswebapptemplate.service.utils.DateService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SendLostPasswordMailCommandHandler(
    private val userDao: UserDao,
    private val dateService: DateService,
    private val lostPasswordMailSenderService: LostPasswordMailSenderService,
) : CommandHandler<SendLostPasswordMailCommand, EmptyCommandResponse> {

    val logger = KotlinLogging.logger {}

    override fun handle(
        command: SendLostPasswordMailCommand,
        userSession: UserSession?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): EmptyCommandResponse {
        if (userSession != null) {
            throw RuntimeException("User is already logged in: $userSession")
        }
        val cleanMail = UserService.cleanMail(command.mail)
        val user = userDao.fetchOrNullByMail(cleanMail)
        if (user == null) {
            logger.info { "Unknown user ${command.mail}" }
            lostPasswordMailSenderService.sendLostPasswordNoAccountMail(
                command.mail,
                // TODO[tmpl] use the language from the request
                Language.En)
            return EmptyCommandResponse
        }
        val now = dateService.now()
        // if mail wasn't validated yet, we can do it
        if (user.status == UserStatus.MailValidationPending) {
            userDao.updateStatus(user.id, UserStatus.Active, now)
        }
        lostPasswordMailSenderService.sendLostPasswordMail(user)
        return EmptyCommandResponse
    }
}
