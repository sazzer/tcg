package uk.co.grahamcox.tcg.authentication.token

import java.time.Instant

/**
 * The details of the encoded access token
 * @property accessToken The access token
 * @property userId The ID of the User this Access Token is for
 * @property expires the expiry of the encoded access token
 */
data class EncodedAccessToken(
        val accessToken: String,
        val userId: String,
        val expires: Instant
)