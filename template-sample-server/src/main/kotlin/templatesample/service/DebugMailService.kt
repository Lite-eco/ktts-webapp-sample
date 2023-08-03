package templatesample.service

import templatesample.domain.ApplicationEnvironment
import templatesample.service.MailService.MailJetMail
import templatesample.service.utils.ApplicationTaskExecutor
import mu.KotlinLogging
import okhttp3.Credentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DebugMailService(
    @Value("\${mailjet.url}") val url: String,
    @Value("\${mailjet.api-key}") val apiKey: String,
    @Value("\${mailjet.secret-key}") val secretKey: String,
    @Value("\${mail.devLogSender}") val devLogSenderMail: String,
    @Value("\${mail.devDestination}") val devDestinationMail: String,
    val httpService: HttpService,
    val taskExecutor: ApplicationTaskExecutor
) {

    private val logger = KotlinLogging.logger {}

    private data class DebugMailJetMessage(
        val From: MailJetMail,
        val To: List<MailJetMail>,
        val Subject: String,
        val HTMLPart: String,
        val MonitoringCategory: String?
    )

    private data class DebugMailJetMessages(val Messages: List<DebugMailJetMessage>)

    // TODO[tmpl] warn if devDestination empty at start (async)
    fun devMail(mailSubject: String, mailContent: String, monitoringCategory: String? = null) {
        // TODO[tmpl] in conf instead ?
        if (ApplicationInstance.env !in
            setOf(ApplicationEnvironment.dev, ApplicationEnvironment.test)) {
            taskExecutor.execute {
                val body =
                    DebugMailJetMessages(
                        listOf(
                            DebugMailJetMessage(
                                MailJetMail(devLogSenderMail, "Template sample app"),
                                listOf(MailJetMail(devDestinationMail, "dev template-sample")),
                                "[${ApplicationInstance.env}] $mailSubject",
                                mailContent,
                                monitoringCategory)))
                val json = MailService.mailJetObjectMapper.writeValueAsString(body)
                val r =
                    try {
                        httpService.postAndReturnString(
                            url,
                            json,
                            HttpService.Header.Authorization to
                                Credentials.basic(apiKey, secretKey))
                    } catch (e: Exception) {
                        logger.error { "Failed to send debug mail" }
                        throw e
                    }
                if (r.code != 200) {
                    val body =
                        when (r) {
                            is HttpService.MaybeStringResponse.EmptyResponse -> "[no response body]"
                            is HttpService.MaybeStringResponse.StringResponse -> r.body
                        }
                    logger.error { "Couldn't send mail :\n$body\n----\n$json" }
                }
            }
        }
    }
}
