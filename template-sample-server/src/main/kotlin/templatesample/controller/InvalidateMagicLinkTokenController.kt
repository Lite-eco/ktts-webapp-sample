package templatesample.controller

import templatesample.service.user.MagicLinkTokenService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class InvalidateMagicLinkTokenController(val magicLinkTokenService: MagicLinkTokenService) {

    private val logger = KotlinLogging.logger {}

    companion object {
        const val invalidateTokenUri = "invalidate-token"
    }

    @GetMapping("/$invalidateTokenUri")
    fun invalidateToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
        mav: ModelAndView
    ): ModelAndView {
        val magicToken = request.getParameter(IndexController.magicTokenParameterName)
        // TODO[tmpl][secu] delete sesssions
        if (magicToken != null) {
            logger.warn { "Invalidate magic token $magicToken" }
            magicLinkTokenService.invalidate(magicToken)
        } else {
            logger.warn { "No magic token to invalidate" }
        }
        return ModelAndView("redirect:/")
    }
}
