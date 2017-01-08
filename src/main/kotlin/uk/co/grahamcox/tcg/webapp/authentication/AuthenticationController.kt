package uk.co.grahamcox.tcg.webapp.authentication

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Controller with which we can manage authentication
 */
@Controller
@RequestMapping("/api/authentication")
class AuthenticationController {
    /**
     * Start authentication with a particular provider
     * @param provider The provider to start authentication with
     */
    @RequestMapping("/{provider}/start")
    fun start(@PathVariable provider: String) = "redirect:http://www.google.com"
}