package uk.co.grahamcox.tcg.webapp.authentication

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import uk.co.grahamcox.tcg.authentication.AuthenticationProviderRegistry
import uk.co.grahamcox.tcg.authentication.token.AccessToken
import uk.co.grahamcox.tcg.authentication.token.AccessTokenEncoder
import uk.co.grahamcox.tcg.authentication.token.AccessTokenGenerator
import uk.co.grahamcox.tcg.authentication.token.EncodedAccessToken
import uk.co.grahamcox.tcg.user.UserId

/**
 * Controller with which we can manage authentication
 * @property authenticationProviderRegistry The means to actually authenticate the user
 * @property accessTokenGenerator The means to generate an access token for the user
 * @property accessTokenEncoder the means to encode an access token for the client
 */
@Controller
@RequestMapping("/api/authentication")
class AuthenticationController(
        private val authenticationProviderRegistry: AuthenticationProviderRegistry,
        private val accessTokenGenerator: AccessTokenGenerator,
        private val accessTokenEncoder: AccessTokenEncoder) {

    /**
     * Handle when the Authentication Provider is unknown
     */
    @ExceptionHandler(UnknownProviderException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun unknownProvider() {}

    /**
     * Start authentication with a particular provider
     * @param provider The provider to start authentication with
     * @return a redirect to the provider, or a 404 Not Found if the provider isn't one we support
     */
    @RequestMapping("/{provider}/start")
    fun start(@PathVariable provider: String): Any {
        val authenticationProvider =
                authenticationProviderRegistry.getProvider(provider) ?: throw UnknownProviderException()

        return RedirectView(authenticationProvider.start().uri.toString())
    }

    /**
     * Handle the redirect back to us from the external provider
     * @param provider The provider we are handling this for
     * @param params The query parameters from the provider
     * @return An access token, or a 404 Not Found if the provider isn't one we support.
     */
    @RequestMapping("/{provider}/redirect", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody
    fun callbackAsJson(@PathVariable provider: String, @RequestParam params: Map<String, Any>): EncodedAccessToken {
        val authenticationProvider =
                authenticationProviderRegistry.getProvider(provider) ?: throw UnknownProviderException()

        val user = authenticationProvider.handleCallback(params)
        val accessToken = accessTokenGenerator.generateAccessToken(user)
        return accessTokenEncoder.encodeAccessToken(accessToken)
    }

    /**
     * Handle the redirect back to us from the external provider
     * @param provider The provider we are handling this for
     * @param params The query parameters from the provider
     * @return An access token, or a 404 Not Found if the provider isn't one we support.
     */
    @RequestMapping("/{provider}/redirect", produces = arrayOf(MediaType.TEXT_HTML_VALUE))
    fun callbackAsHtml(@PathVariable provider: String, @RequestParam params: Map<String, Any>): ModelAndView {
        val accessToken = callbackAsJson(provider, params)
        return ModelAndView("accessToken", mapOf("accessToken" to accessToken))
    }

    /**
     * Handler to get the details of the access token that we are using, if any
     * @return the access token
     */
    @RequestMapping("/accessToken")
    @ResponseBody
    fun getCurrentAccessToken(accessToken: AccessToken?) =
            when (accessToken) {
                null -> ResponseEntity.notFound().build()
                else -> ResponseEntity.ok(accessToken)
            }

    /**
     * Handler to get the details of the User ID from the Access Token that we are using, if any
     * @return the User ID
     */
    @RequestMapping("/userId")
    @ResponseBody
    fun getCurrentUserId(userId: UserId?) =
            when (userId) {
                null -> ResponseEntity.notFound().build()
                else -> ResponseEntity.ok(userId.id)
            }
}