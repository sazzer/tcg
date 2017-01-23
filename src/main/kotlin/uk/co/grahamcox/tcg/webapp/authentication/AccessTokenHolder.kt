package uk.co.grahamcox.tcg.webapp.authentication

import uk.co.grahamcox.tcg.authentication.token.AccessToken

/**
 * Mechanism to store the Access Token for the current request
 */
interface AccessTokenHolder {
    /**
     * Retrieve the Access Token for the current request
     * @return the Access Token
     */
    fun retrieveAccessToken() : AccessToken?
}