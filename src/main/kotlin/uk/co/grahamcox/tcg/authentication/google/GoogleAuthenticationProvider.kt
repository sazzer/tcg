package uk.co.grahamcox.tcg.authentication.google

import org.slf4j.LoggerFactory
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.tcg.authentication.AuthenticationProvider
import uk.co.grahamcox.tcg.authentication.NonceGenerator
import uk.co.grahamcox.tcg.authentication.RedirectDetails
import uk.co.grahamcox.tcg.authentication.RedirectGenerator

/**
 * Implementation of the Authentication Provider for use against Google
 * @property config The Google configuration
 * @property nonceGenerator The nonce generator to use
 * @property redirectGenerator The redirect generator to use
 */
class GoogleAuthenticationProvider(
        private val config: GoogleConfig,
        private val nonceGenerator: NonceGenerator,
        private val redirectGenerator: RedirectGenerator
) : AuthenticationProvider {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(GoogleAuthenticationProvider::class.java)
    }
    /**
     * Start authentication for this provider
     * @return the details of the redirect to the provider
     */
    override fun start(): RedirectDetails {
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

        LOG.debug("Generated redirect URI: {}", uri)
        return RedirectDetails(uri)
    }

    /**
     * Handle the callback from the provider after authentication has finished
     * @param params The parameters from the provider
     */
    override fun handleCallback(params: Map<String, Any>) {
        LOG.info("Callback parameters: {}", params)
    }
}