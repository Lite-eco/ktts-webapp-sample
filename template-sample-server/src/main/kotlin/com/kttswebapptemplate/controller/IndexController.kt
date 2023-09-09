package com.kttswebapptemplate.controller

import com.kttswebapptemplate.config.Routes
import com.kttswebapptemplate.domain.ApplicationBootstrapData
import com.kttswebapptemplate.domain.UserInfos
import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.serialization.Serializer.serialize
import com.kttswebapptemplate.service.user.UserSessionService
import com.kttswebapptemplate.service.utils.ApplicationInstance
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.File
import java.nio.file.Files
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController(
    @Value("\${assets.webpackDevPort}") private val assetsWebpackDevPort: String,
    @Value("\${assets.useBuildFiles}") private val assetsUseBuildFiles: Boolean,
    private val userDao: UserDao,
    private val userSessionService: UserSessionService
) {

    companion object {
        fun extractDomain(url: String) =
            url.substring(url.indexOf("://") + 3).let {
                val domainLength =
                    listOf(it.indexOf("/"), it.indexOf(":"), it.length)
                        .filter { it >= 0 }
                        .minOrNull()
                        ?: throw IllegalArgumentException()
                it.substring(0, domainLength)
            }
    }

    val buildAssets by lazy {
        File(System.getProperty("user.dir") + "/asset-manifest.json")
            .let { Files.readString(it.toPath()) }
            .let { JSONObject(it ?: throw RuntimeException()) }
            .getJSONArray("entrypoints")
            .map { "/$it" }
    }

    val cssAssets by lazy {
        if (assetsUseBuildFiles) {
            buildAssets.filter { it.endsWith(".css") }
        } else {
            emptyList()
        }
    }

    val builtJsAssets by lazy { buildAssets.filter { it.endsWith(".js") } }

    @GetMapping(Routes.logout) fun redirect() = "redirect:${Routes.root}"

    @GetMapping(Routes.root, "/*", "/{path:^(?!static)[^\\.]*}/**")
    fun index(
        request: HttpServletRequest,
        response: HttpServletResponse,
        mav: ModelAndView
    ): ModelAndView {
        val userInfos =
            if (userSessionService.isAuthenticated()) {
                val userSession = userSessionService.getUserSession()
                val user = userDao.fetch(userSession.userId)
                UserInfos.fromUser(user)
            } else null
        mav.model["bootstrapData"] =
            serialize(ApplicationBootstrapData(ApplicationInstance.env, userInfos))
        mav.model["deploymentId"] = ApplicationInstance.deploymentLogId.stringUuid()
        mav.model["gitRevisionLabel"] = ApplicationInstance.gitRevisionLabel
        mav.model["jsAssets"] = jsAssets(request)
        mav.model["cssAssets"] = cssAssets
        mav.viewName = "index"
        return mav
    }

    fun jsAssets(request: HttpServletRequest) =
        if (assetsUseBuildFiles) {
            builtJsAssets
        } else {
            val domain = request.scheme + "://" + extractDomain(request.requestURL.toString())
            listOf("bundle.js", "vendors~main.chunk.js", "main.chunk.js").map {
                "$domain:$assetsWebpackDevPort/static/js/$it"
            }
        }
}
