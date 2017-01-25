package uk.co.grahamcox.tcg.authentication.token

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
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
    override fun generateAccessToken(user: Model<UserId, UserData>) = AccessToken(
            tokenId = TokenId(UUID.randomUUID().toString()),
            userId = user.identity.id
    )
}