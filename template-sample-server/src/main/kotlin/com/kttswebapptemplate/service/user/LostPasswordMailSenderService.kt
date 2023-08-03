package com.kttswebapptemplate.service.user

import com.kttswebapptemplate.config.ApplicationConstants
import com.kttswebapptemplate.config.Routes
import com.kttswebapptemplate.controller.IndexController
import com.kttswebapptemplate.controller.InvalidateMagicLinkTokenController
import com.kttswebapptemplate.domain.MailReference
import com.kttswebapptemplate.domain.Uri
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.serialization.Serializer.serialize
import com.kttswebapptemplate.service.mail.MailService
import com.kttswebapptemplate.service.utils.ApplicationInstance
import com.kttswebapptemplate.service.utils.HttpService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LostPasswordMailSenderService(
    @Value("\${app.url}") private val appUrl: Uri,
    private val httpService: HttpService,
    private val magicLinkTokenService: MagicLinkTokenService,
    private val mailService: MailService,
) {

    private val logger = KotlinLogging.logger {}

    data class LostPasswordMailPayload(val url: Uri, val invalidateTokenUrl: Uri)

    fun sendMail(user: UserDao.Record) {
        val magicToken = magicLinkTokenService.createToken(user.id)
        val magicUrl =
            appUrl
                .resolve(Routes.loginUpdatePassword)
                .append("?${IndexController.magicTokenParameterName}=$magicToken")
        val invalidateUrl =
            appUrl
                .resolve(InvalidateMagicLinkTokenController.invalidateTokenUri)
                .append("?${IndexController.magicTokenParameterName}=$magicToken")
        val data = LostPasswordMailPayload(magicUrl, invalidateUrl)
        val mailContent = fetchMailContent(data)
        logger.info { "Send lost password mail to $user" }
        mailService.sendMail(
            ApplicationConstants.applicationMailSenderName,
            ApplicationConstants.applicationMail,
            user.mail,
            user.mail,
            "Change your password",
            mailContent,
            MailReference.LostPassword,
            MailService.MailLog.DoLog,
            MailService.MailLogProperties(
                ApplicationInstance.deploymentLogId, user.id, serialize(data)))
    }

    fun fetchMailContent(data: LostPasswordMailPayload): String {
        //        val json = serialize(data)
        //        val httpResponse =
        // httpService.postAndReturnString("$nodeServerUrl/mail/${MailReference.LOST_PASSWORD.name}", json,
        //                HttpService.RequestBodyType.JSON)
        //        return when (httpResponse.code) {
        //            200 -> httpResponse.body ?: throw RuntimeException("Node server no body")
        //            else -> throw RuntimeException("Node server responded ${httpResponse.code},
        // message : ${httpResponse.body}")
        //        }
        // TODO[tmpl][user]
        return "bonjour"
    }
}
