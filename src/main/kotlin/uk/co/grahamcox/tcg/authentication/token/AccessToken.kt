package uk.co.grahamcox.tcg.authentication.token

import uk.co.grahamcox.tcg.user.UserId

/**
 * The representation of an Access Token
 * @property tokenId The internal ID of the access token
 * @property userId The User that the access token is for
 */
data class AccessToken(
        val tokenId: TokenId,
        val userId: UserId
)