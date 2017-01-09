package uk.co.grahamcox.tcg.authentication.google

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Representation of an Access Token as received from Google
 * @property accessToken The actual Access Token
 * @property tokenType The type of the Access Token
 * @property expiresIn The expiry time of the Access Token
 * @property idToken The ID Token
 */
data class AccessTokenResponse @JsonCreator constructor(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("token_type") val tokenType: String,
        @JsonProperty("expires_in") val expiresIn: Int,
        @JsonProperty("id_token", required = false) val idToken: String?
)