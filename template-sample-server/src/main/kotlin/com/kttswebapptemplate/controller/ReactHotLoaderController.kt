package com.kttswebapptemplate.controller

import com.kttswebapptemplate.domain.ApplicationEnvironment
import com.kttswebapptemplate.domain.MimeType
import com.kttswebapptemplate.error.TemplateSampleNotFoundException
import com.kttswebapptemplate.service.utils.ApplicationInstance
import com.kttswebapptemplate.service.utils.HttpService
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
    @Value("\${assets.webpackDevPort}") private val assetsWebpackDevPort: String,
    @Value("\${assets.webpackDevHost}") private val assetsWebpackDevHost: String,
    private val httpService: HttpService
) {

    val logger = KotlinLogging.logger {}

    @GetMapping("/*.hot-update.*")
    fun handle(request: HttpServletRequest, response: HttpServletResponse) {
        if (ApplicationInstance.env != ApplicationEnvironment.Dev) {
            throw TemplateSampleNotFoundException()
        }
        val path = request.servletPath
        response.contentType =
            if (path.endsWith(".js")) {
                MimeType.Javascript.fullType
            } else {
                MimeType.Json.fullType
            }
        val r =
            httpService.execute(
                HttpMethod.GET, "http://$assetsWebpackDevHost:$assetsWebpackDevPort$path")
        if (r.code == HttpStatus.OK) {
            response.writer.print(r.bodyString)
        } else {
            logger.error { "Error webpack hot update $r" }
        }
    }
}
