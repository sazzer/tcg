package uk.co.grahamcox.tcg.webapp.authentication

import uk.co.grahamcox.tcg.authentication.token.AccessToken

/**
 * Implementation of the Access Token Holder that allows for the access token to be stored and retrieved
 */
class AccessTokenStore : AccessTokenHolder {

    /** The actual Access Token storage */
    private val accessTokenStore = ThreadLocal<AccessToken>()

    /**
     * Set the Access Token for this current request
     * @param accessToken The access token
     */
    fun set(accessToken: AccessToken) {
        accessTokenStore.set(accessToken)
    }

    /**
     * Remove the access token for the current request
     */
    fun remove() {
        accessTokenStore.remove()
    }

    /**
     * Retrieve the Access Token for the current request
     * @return the Access Token
     */
    override fun retrieveAccessToken(): AccessToken? = accessTokenStore.get()
}