package templatesample.controller

import templatesample.domain.ApplicationEnvironment
import templatesample.domain.MimeType
import templatesample.error.TemplateSampleNotFoundException
import templatesample.service.ApplicationInstance
import templatesample.service.HttpService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
// [doc] so it won't run on prod
@ConditionalOnExpression("!\${assets.useBuildFiles}")
class ReactHotLoaderController(
    @Value("\${assets.webpackDevPort}") val assetsWebpackDevPort: String,
    @Value("\${assets.webpackDevHost}") val assetsWebpackDevHost: String,
    val httpService: HttpService
) {

    val logger = KotlinLogging.logger {}

    @GetMapping("/*.hot-update.*")
    fun handle(request: HttpServletRequest, response: HttpServletResponse) {
        if (ApplicationInstance.env != ApplicationEnvironment.dev) {
            throw TemplateSampleNotFoundException()
        }
        val path = request.servletPath
        response.contentType =
            if (path.endsWith(".js")) {
                MimeType.javascript.fullType
            } else {
                MimeType.json.fullType
            }
        val r =
            httpService.execute(
                HttpMethod.GET, "http://$assetsWebpackDevHost:$assetsWebpackDevPort$path")
        if (r.code == HttpStatus.OK) {
            response.writer.print(r.body)
        } else {
            logger.error { "Error webpack hot update $r" }
        }
    }
}
