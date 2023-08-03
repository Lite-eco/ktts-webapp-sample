package templatesample.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedField
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import templatesample.domain.ApplicationEnvironment
import templatesample.domain.DeploymentLogId
import templatesample.domain.MailLogId
import templatesample.domain.MailReference
import templatesample.domain.MimeType
import templatesample.domain.UserId
import templatesample.error.MessageNotSentException
import templatesample.repository.log.MailLogDao
import java.util.Base64
import mu.KotlinLogging
import okhttp3.Credentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MailService(
    @Value("\${mailjet.url}") val url: String,
    @Value("\${mailjet.api-key}") val apiKey: String,
    @Value("\${mailjet.secret-key}") val secretKey: String,
    @Value("\${mail.devLogSender}") val devLogSenderMail: String,
    val httpService: HttpService,
    val mailLogDao: MailLogDao,
    val dateService: DateService,
    val randomService: RandomService
) {

    private val logger = KotlinLogging.logger {}

    companion object {
        val mailJetObjectMapper by lazy {
            ObjectMapper().apply {
                propertyNamingStrategy = MyPropertyNamingStrategy()
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
    }

    // TODO[tmpl][mail] the uppercase on first letter : ask it to the serializer ?
    data class MailJetMail(val Email: String, val Name: String)

    // TODO[tmpl][mail] is it possible to limit the strings here ?
    data class MailJetAttachment(
        val ContentType: String,
        val Filename: String,
        val Base64Content: String
    )

    private data class MailJetMessage(
        val From: MailJetMail,
        val To: List<MailJetMail>,
        val Subject: String,
        // TODO[tmpl] ?
        //                          val TextPart: String,
        val HTMLPart: String,
        val Attachments: List<MailJetAttachment>,
        val CustomID: String,
        val CustomCampaign: String,
        val EventPayload: String
    )

    private data class MailJetMessages(val Messages: List<MailJetMessage>)

    // TODO[tmpl][mail] [doc] this informations is here to display the environnement which sent the
    // mail in mailjet ui
    private data class MailJetEventPayload(val env: String)

    // TODO[tmpl][mail] check this... + uppercase first letter handled here ?
    private class MyPropertyNamingStrategy : PropertyNamingStrategy() {
        override fun nameForField(
            config: MapperConfig<*>?,
            field: AnnotatedField,
            defaultName: String
        ) = convert(field.name)

        override fun nameForGetterMethod(
            config: MapperConfig<*>?,
            method: AnnotatedMethod,
            defaultName: String
        ) = convert(method.name.toString())

        override fun nameForSetterMethod(
            config: MapperConfig<*>?,
            method: AnnotatedMethod,
            defaultName: String
        ) = convert(method.name.toString())

        private fun convert(input: String) = input.substring(3)
    }

    enum class MailLog {
        doLog,
        doNotLog
    }

    data class MailLogProperties(
        val deploymentLogId: DeploymentLogId,
        val userId: UserId,
        val jsonData: String
    )

    data class Attachment(val filename: String, val content: ByteArray, val contentType: MimeType)

    fun sendMail(
        senderName: String,
        senderMail: String,
        recipientName: String,
        recipientMail: String,
        mailSubject: String,
        mailContent: String,
        mailReference: MailReference,
        logMail: MailLog,
        mailLogProperties: MailLogProperties? = null,
        attachments: List<Attachment>? = null
    ): MailLogId? {
        if (ApplicationInstance.env == ApplicationEnvironment.dev &&
            recipientMail != devLogSenderMail) {
            throw IllegalArgumentException("Mail send canceled en env dev to ${recipientMail}")
        }
        val mailLogId = randomService.id<MailLogId>()
        val payload =
            mailJetObjectMapper.writeValueAsString(
                MailJetEventPayload(ApplicationInstance.env.name))
        val subject =
            if (ApplicationInstance.env == ApplicationEnvironment.prod) mailSubject
            else "[${ApplicationInstance.env}] $mailSubject"
        val mailJetAttachments =
            (attachments ?: emptyList()).map {
                MailJetAttachment(
                    it.contentType.fullType,
                    it.filename,
                    Base64.getEncoder().encodeToString(it.content))
            }
        val body =
            MailJetMessages(
                listOf(
                    MailJetMessage(
                        MailJetMail(senderMail, senderName),
                        listOf(MailJetMail(recipientMail, recipientName)),
                        subject,
                        mailContent,
                        mailJetAttachments,
                        mailLogIdToString(mailLogId),
                        mailReference.name,
                        payload)))
        val json = mailJetObjectMapper.writeValueAsString(body)
        val response =
            try {
                httpService.postAndReturnString(
                    url,
                    json,
                    HttpService.Header.Authorization to Credentials.basic(apiKey, secretKey))
            } catch (e: Exception) {
                logger.error {
                    "Failed to send mail to $recipientMail. Mail log properties & content is following =>"
                }
                logger.info { mailLogProperties }
                logger.info { mailContent }
                throw e
            }
        when (response.code) {
            200 -> {}
            else -> {
                logger.trace { response }
                throw MessageNotSentException("${response.code} $recipientMail $mailSubject")
            }
        }
        return when (logMail) {
            MailLog.doLog -> {
                mailLogProperties ?: throw IllegalArgumentException("$recipientMail $mailSubject")
                try {
                    mailLogDao.insert(
                        MailLogDao.Record(
                            mailLogId,
                            mailLogProperties.deploymentLogId,
                            mailLogProperties.userId,
                            mailReference,
                            recipientMail,
                            mailLogProperties.jsonData,
                            subject,
                            mailContent,
                            dateService.now()))
                    logger.info { "Mail sent & logged to $recipientMail" }
                } catch (e: Exception) {
                    logger.error {
                        "Mail sent but failed to log mail to $recipientMail. Mail log properties & content is " +
                            "following =>"
                    }
                    logger.info { mailLogProperties }
                    logger.info { mailContent }
                    throw e
                }
                mailLogId
            }
            MailLog.doNotLog -> {
                logger.info { "Mail sent (no log) to $recipientMail" }
                null
            }
        }
    }

    fun mailLogIdToString(mailLogId: MailLogId) = mailLogId.rawId.toString()
}
