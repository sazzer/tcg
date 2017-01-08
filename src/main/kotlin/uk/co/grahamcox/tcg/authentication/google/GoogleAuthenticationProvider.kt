package uk.co.grahamcox.tcg.authentication.google

import uk.co.grahamcox.tcg.authentication.AuthenticationProvider
import uk.co.grahamcox.tcg.authentication.RedirectDetails
import java.net.URI

/**
 * Implementation of the Authentiaction Provider for use against Google
 */
class GoogleAuthenticationProvider : AuthenticationProvider {
    /**
     * Start authentication for this provider
     */
    override fun start() = RedirectDetails(
            uri = URI("http://www.twitter.com")
    )
}