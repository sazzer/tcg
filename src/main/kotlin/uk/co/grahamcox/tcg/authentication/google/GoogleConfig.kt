package uk.co.grahamcox.tcg.authentication.google

import java.net.URI

/**
 * The Google Authentication config to use
 * @property clientId The Google Client ID
 * @property clientSecret The Google Client Secret
 * @property authUrlBase The Base URL to redirect the client to
 * @property tokenUrl The URL to get the Access Token from
 * @property userProfileUrl The URL to get the User Profile from
 */
data class GoogleConfig(
        val clientId: String,
        val clientSecret: String,
        val authUrlBase: URI,
        val tokenUrl: URI,
        val userProfileUrl: URI
)