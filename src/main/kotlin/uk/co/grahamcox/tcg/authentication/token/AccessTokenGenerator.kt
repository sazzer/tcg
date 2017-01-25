package uk.co.grahamcox.tcg.authentication.token

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId

/**
 * Generator to generate an Access Token for the given User
 */
interface AccessTokenGenerator {
    /**
     * Generate the Access Token
     * @param user the user the access token is for
     * @return the access token
     */
    fun generateAccessToken(user: Model<UserId, UserData>) : AccessToken
}