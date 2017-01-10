package uk.co.grahamcox.tcg.authentication.google

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.authentication.AuthenticationProvider
import uk.co.grahamcox.tcg.authentication.RedirectDetails
import uk.co.grahamcox.tcg.user.UserRetriever

/**
 * Implementation of the Authentication Provider for use against Google
 * @property redirectBuilder The builder for the Google Authentication Redirect details
 * @property accessTokenRetriever The means to get an Access Token from Google
 * @property userProfileRetriever The means to get a User Profile from Google
 * @property userRetriever The means to load a User record from our database
 */
class GoogleAuthenticationProvider(
        private val redirectBuilder: GoogleAuthenticationRedirectBuilder,
        private val accessTokenRetriever: GoogleAccessTokenRetriever,
        private val userProfileRetriever: UserProfileRetriever,
        private val userRetriever: UserRetriever
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
        val accessToken = accessTokenRetriever.retrieveAccessToken(params["code"]!!.toString())
        val userProfile = userProfileRetriever.retrieveUserProfile(accessToken.accessToken)
        val userModel = userRetriever.retrieveUserByProviderId("google", userProfile.id)
        if (userModel == null) {
            LOG.info("Unknown user has logged in. Creating user record")
        } else {
            LOG.debug("User {} has logged in", userModel)
        }
    }
}