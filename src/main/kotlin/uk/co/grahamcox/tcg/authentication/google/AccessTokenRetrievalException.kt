package uk.co.grahamcox.tcg.authentication.google

/**
 * Exception when an error occurred retrieving the access token
 * @param message The error message
 */
class AccessTokenRetrievalException(message: String = "Failed to retrieve Access Token") : RuntimeException(message)