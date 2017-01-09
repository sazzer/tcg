package uk.co.grahamcox.tcg.webapp.authentication

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.RedirectView
import uk.co.grahamcox.tcg.authentication.AuthenticationProviderRegistry

/**
 * Controller with which we can manage authentication
 */
@Controller
@RequestMapping("/api/authentication")
class AuthenticationController(private val authenticationProviderRegistry: AuthenticationProviderRegistry) {
    /**
     * Start authentication with a particular provider
     * @param provider The provider to start authentication with
     * @return a redirect to the provider, or a 404 Not Found if the provider isn't one we support
     */
    @RequestMapping("/{provider}/start")
    fun start(@PathVariable provider: String): Any {
        val authenticationProvider = authenticationProviderRegistry.getProvider(provider)
        return if (authenticationProvider != null) {
            RedirectView(authenticationProvider.start().uri.toString())
        } else {
            ResponseEntity<Void>(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Handle the redirect back to us from the external provider
     * @param provider The provider we are handling this for
     * @param params The query parameters from the provider
     */
    @RequestMapping("/{provider}/redirect")
    fun callback(@PathVariable provider: String, params: Map<String, Any>) {
        val authenticationProvider = authenticationProviderRegistry.getProvider(provider)

        if (authenticationProvider != null) {
            authenticationProvider.handleCallback(params)
        }
    }
}