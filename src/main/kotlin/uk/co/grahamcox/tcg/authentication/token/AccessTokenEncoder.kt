package uk.co.grahamcox.tcg.authentication.token

/**
 * Mechanism to encode an access token to a string, and vice versa
 */
interface AccessTokenEncoder {
    /**
     * Encode the given Access Token into a String
     * @param accessToken The access token to encode
     * @return the encoded access token
     */
    fun encodeAccessToken(accessToken: AccessToken): String

    /**
     * Decode the given Access Token from a String
     * @param accessToken The access token to decode
     * @return the decoded access token
     */
    fun decodeAccessToken(accessToken: String): AccessToken
}