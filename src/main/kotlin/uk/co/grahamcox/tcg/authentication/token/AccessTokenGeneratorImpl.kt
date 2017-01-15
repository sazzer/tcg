package uk.co.grahamcox.tcg.authentication.token

import uk.co.grahamcox.tcg.user.UserModel
import java.util.*

/**
 * Standard implementation of the Access TOken Generator
 */
class AccessTokenGeneratorImpl : AccessTokenGenerator {
    /**
     * Generate the Access Token
     * @param user the user the access token is for
     * @return the access token
     */
    override fun generateAccessToken(user: UserModel) = AccessToken(
            tokenId = TokenId(UUID.randomUUID().toString()),
            userId = user.identity.id
    )
}