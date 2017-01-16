package uk.co.grahamcox.tcg.authentication.token

import java.time.Instant

/**
 * The details of the encoded access token
 * @property accessToken The access token
 * @property expires the expiry of the encoded access token
 */
data class EncodedAccessToken(
        val accessToken: String,
        val expires: Instant
)