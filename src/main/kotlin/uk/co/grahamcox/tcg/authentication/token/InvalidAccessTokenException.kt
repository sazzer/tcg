package uk.co.grahamcox.tcg.authentication.token

/**
 * Exception to indicate that a decoded Access Token was invalid
 */
class InvalidAccessTokenException(message: String) : RuntimeException(message)