package uk.co.grahamcox.tcg.authentication.google

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.authentication.AuthenticationProvider
import uk.co.grahamcox.tcg.authentication.RedirectDetails

/**
 * Implementation of the Authentication Provider for use against Google
 * @property redirectBuilder The builder for the Google Authentication Redirect details
 */
class GoogleAuthenticationProvider(
        private val redirectBuilder: GoogleAuthenticationRedirectBuilder,
        private val accessTokenRetriever: GoogleAccessTokenRetriever
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
        val redirectDetails = redirectBuilder.buildRedirectDetails()

        LOG.debug("Generated redirect URI: {}", redirectDetails)
        return redirectDetails
    }

    /**
     * Handle the callback from the provider after authentication has finished
     * @param params The parameters from the provider
     */
    override fun handleCallback(params: Map<String, Any>) {
        LOG.info("Callback parameters: {}", params)
        accessTokenRetriever.retrieveAccessToken(params["code"]!!.toString())
    }
}