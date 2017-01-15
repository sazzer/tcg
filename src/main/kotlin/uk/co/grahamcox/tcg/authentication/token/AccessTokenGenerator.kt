package uk.co.grahamcox.tcg.authentication.token

import uk.co.grahamcox.tcg.user.UserModel

/**
 * Generator to generate an Access Token for the given User
 */
interface AccessTokenGenerator {
    /**
     * Generate the Access Token
     * @param user the user the access token is for
     * @return the access token
     */
    fun generateAccessToken(user: UserModel) : AccessToken
}