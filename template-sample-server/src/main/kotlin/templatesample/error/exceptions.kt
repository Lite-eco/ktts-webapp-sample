package templatesample.error

import java.time.Instant
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.event.Level
import templatesample.domain.RequestErrorId
import templatesample.domain.TemplateSampleId

// FIXME[tmpl] can remove ? ReadableStackTrace ? and ReadableStackTraceSerializer ?
// @Shared
data class RequestError(
    val id: RequestErrorId,
    val status: Int,
    val error: String,
    val message: String,
    val instant: Instant,
    val stackTrace: ReadableStackTrace?
)

data class ReadableStackTrace(val exception: Throwable?) {
    fun toReadableString(): String? =
        if (exception != null) ExceptionUtils.getStackTrace(exception) else null
}

// TODO[tmpl][error] display ? silent ? mute ?
// class DisplayMessageException(displayMessage: String, logMessage: String) : Exception(message)
// user message exception
data class DisplayMessageException(
    val displayMessage: String,
    val logMessage: String,
    val logLevel: Level
) : Exception(logMessage)

// [doc] contains mail to be clear on the front ? Not really needed it smartly handled on the front
// ?
data class MailAlreadyRegisteredException(val mail: String) : Exception()

// TODO[tmpl] this message ?
data class ItemIdNotFoundException(val id: TemplateSampleId<*>) : Exception("Not found : $id")

// TODO[tmpl] a message
data class ItemNotFoundException(val itemClass: Class<*>, val reference: String) : Exception()

// TODO[tmpl] ItemIdNotFoundException + ItemNotFoundException + TemplateSampleNotFoundException...
// it's a lot of "not found"
class TemplateSampleNotFoundException : Exception()

class TemplateSampleSerializationLocalDateException(val date: String) : Exception()

// TODO[tmpl][error] always use runtime ? because of kt
class MessageNotSentException(message: String) : RuntimeException(message)

class TemplateSampleSecurityException(message: String) : Exception(message)

class UserLoggedOutException : Exception()
