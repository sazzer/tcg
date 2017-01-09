package uk.co.grahamcox.tcg.authentication.google

import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.tcg.authentication.NonceGenerator
import uk.co.grahamcox.tcg.authentication.RedirectDetails
import uk.co.grahamcox.tcg.authentication.RedirectGenerator

/**
 * Mechanism to build the Redirect Details to start authentication against Google
 * @property config The Google configuration
 * @property nonceGenerator The nonce generator to use
 * @property redirectGenerator The redirect generator to use
 */
class GoogleAuthenticationRedirectBuilder(
        private val config: GoogleConfig,
        private val nonceGenerator: NonceGenerator,
        private val redirectGenerator: RedirectGenerator) {

    /**
     * Start authentication for this provider
     * @return the details of the redirect to the provider
     */
    fun buildRedirectDetails(): RedirectDetails {
        val nonce = nonceGenerator.generate()
        val redirect = redirectGenerator.generate()

        val uri = UriComponentsBuilder.fromUri(config.authUrlBase)
                .queryParam("client_id", config.clientId)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid email")
                .queryParam("redirect_uri", redirect.toString())
                .queryParam("state", nonce)
                .build()
                .toUri()

        return RedirectDetails(uri)
    }
}