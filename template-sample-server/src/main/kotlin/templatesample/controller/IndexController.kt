package templatesample.controller

import templatesample.config.Routes
import templatesample.domain.ApplicationBootstrapData
import templatesample.domain.UserInfos
import templatesample.repository.user.UserDao
import templatesample.serialization.Serializer.serialize
import templatesample.service.ApplicationInstance
import templatesample.service.LocaleService
import templatesample.service.user.MagicLinkTokenService
import templatesample.service.user.UserService
import templatesample.service.user.UserSessionService
import freemarker.ext.beans.BeansWrapperBuilder
import freemarker.template.Configuration
import java.io.File
import java.net.URLEncoder
import java.nio.file.Files
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController(
    @Value("\${assets.webpackDevPort}") val assetsWebpackDevPort: String,
    @Value("\${assets.useBuildFiles}") val assetsUseBuildFiles: Boolean,
    val userDao: UserDao,
    val localeService: LocaleService,
    val userService: UserService,
    val applicationInstance: ApplicationInstance,
    val magicLinkTokenService: MagicLinkTokenService,
    val userSessionService: UserSessionService
) {

    companion object {
        const val magicTokenParameterName = "magicToken"

        val statics =
            BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS)
                .build()
                .staticModels

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
            .let { it.getJSONArray("entrypoints") }
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
        val magicToken = request.getParameter(magicTokenParameterName)
        if (magicToken != null) {
            val queryString = rewriteQueryString(request.parameterMap)
            magicLinkTokenService.connectUser(magicToken, request, response)
            return ModelAndView(
                "redirect:" +
                    request.requestURI +
                    if (queryString.isNotBlank()) "?$queryString" else "")
        }
        val userInfos =
            if (userSessionService.isAuthenticated()) {
                val userSession = userSessionService.getUserSession()
                val user = userDao.fetch(userSession.userId)
                UserInfos.fromUser(user)
            } else null
        mav.model["bootstrapData"] =
            serialize(ApplicationBootstrapData(ApplicationInstance.env, userInfos))
        mav.model["deploymentId"] = applicationInstance.deploymentId.rawId
        mav.model["gitRevisionLabel"] = applicationInstance.gitRevisionLabel
        mav.model["jsAssets"] = jsAssets(request)
        mav.model["cssAssets"] = cssAssets
        mav.model["statics"] = statics
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

    fun rewriteQueryString(parameterMap: Map<String, Array<String>>): String {
        val params = parameterMap.keys.filter { it != magicTokenParameterName }
        return if (params.isEmpty()) {
            ""
        } else {
            params
                .flatMap { paramName ->
                    // TODO[tmpl] works correctly ? is needed ?
                    // srsly test & compare with UriEncoder
                    // + test smthg= (with emtpy string)
                    val paramValues = parameterMap.getValue(paramName)
                    paramValues.map {
                        URLEncoder.encode(paramName, Charsets.UTF_8.name()) +
                            "=" +
                            URLEncoder.encode(it, Charsets.UTF_8.name())
                    }
                }
                .reduce { acc, s -> acc + "&" + s }
        }
    }
}
