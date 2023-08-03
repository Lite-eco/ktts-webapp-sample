package templatesample.service.changepassword

import templatesample.config.ApplicationConstants
import templatesample.config.Routes
import templatesample.controller.IndexController
import templatesample.controller.InvalidateMagicLinkTokenController
import templatesample.domain.MailReference
import templatesample.repository.user.UserDao
import templatesample.serialization.Serializer.serialize
import templatesample.service.ApplicationInstance
import templatesample.service.HttpService
import templatesample.service.MailService
import templatesample.service.user.MagicLinkTokenService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LostPasswordMailSenderService(
    @Value("\${app.url}") val appUrl: String,
    val httpService: HttpService,
    val magicLinkTokenService: MagicLinkTokenService,
    val mailService: MailService,
) {

    private val logger = KotlinLogging.logger {}

    data class LostPasswordMailPayload(val url: String, val invalidateTokenUrl: String)

    fun sendMail(user: UserDao.Record) {
        val magicToken = magicLinkTokenService.createToken(user.id)
        val magicUrl =
            "$appUrl${Routes.loginUpdatePassword}?${IndexController.magicTokenParameterName}=$magicToken"
        val invalidateUrl =
            "$appUrl${InvalidateMagicLinkTokenController.invalidateTokenUri}?${IndexController.magicTokenParameterName}=$magicToken"
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
