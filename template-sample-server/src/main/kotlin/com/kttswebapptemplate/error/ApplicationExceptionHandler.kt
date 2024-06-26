package com.kttswebapptemplate.error

import com.kttswebapptemplate.domain.MimeType
import com.kttswebapptemplate.domain.RequestErrorId
import com.kttswebapptemplate.serialization.Serializer
import com.kttswebapptemplate.service.user.UserSessionService
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.random.RandomService
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

@ControllerAdvice
class ApplicationExceptionHandler(
    private val dateService: DateService,
    private val randomService: RandomService,
    private val userSessionService: UserSessionService
) {

    private val logger = KotlinLogging.logger {}

    companion object {
        val htmlMimeTypes = setOf("text/html", "application/xhtml+xml", "application/xml")
    }

    @ExceptionHandler(Exception::class)
    fun defaultErrorHandler(
        request: WebRequest,
        response: HttpServletResponse,
        exception: Exception
    ): ModelAndView {
        // TODO[tmpl][secu] handle exceptions
        // log userid, mail, ip
        val id = randomService.id<RequestErrorId>()
        val subCause = exception.cause?.cause
        when {
            subCause is SizeLimitExceededException -> {
                logger.info {
                    (userSessionService.getUserSessionIfAuthenticated()?.let { "User $it" }
                        ?: "Anonymous") + " tried to upload a file to big: ${subCause.message}"
                }
                // TODO[tmpl][secu] error codes for the front !
                return render(
                    request,
                    response,
                    RequestError(
                        id, 500, "Error", "File exceeds max authorized size", dateService.now()))
            }
            exception is DisplayMessageException -> {
                // TODO[tmpl][secu] this "DisplayError" is used on front
                logger.info { "[user message exception] ${exception.logMessage}" }
                return render(
                    request,
                    response,
                    RequestError(
                        id, 500, "DisplayError", exception.displayMessage, dateService.now()))
            }
            else -> {
                if (userSessionService.isAuthenticated()) {
                    // TODO duplicates with LogbackUserSessionConverter ?
                    logger.warn(exception) {
                        "Unhandled exception [$id] for user ${userSessionService.getUserSession()}"
                    }
                } else {
                    logger.warn(exception) { "Unhandled exception [$id]" }
                }
                // TODO[tmpl][secu] i18n, & i18n from spring
                // all strings should come from a central place
                return render(
                    request,
                    response,
                    RequestError(id, 500, "Error", "Unknown error", dateService.now()))
            }
        }
    }

    fun render(
        request: WebRequest,
        response: HttpServletResponse,
        requestError: RequestError
    ): ModelAndView {
        val mav = ModelAndView()
        response.status = requestError.status
        val returnHtml =
            request.getHeader("Accept")?.let { accept -> htmlMimeTypes.any { it in accept } }
                ?: false
        if (returnHtml) {
            mav.model["requestErrorId"] = requestError.id.stringUuid()
            mav.model["requestError"] = requestError
            mav.viewName = "error"
        } else {
            response.contentType = MimeType.ApplicationJson.type
            // write in the buffer with
            // objectMapper.writeValue(response.outputStream, requestErrorNode)
            // is buggy...
            mav.view = MappingJackson2JsonView(Serializer.objectMapper)
            mav.model["requestError"] = requestError
        }
        return mav
    }
}
