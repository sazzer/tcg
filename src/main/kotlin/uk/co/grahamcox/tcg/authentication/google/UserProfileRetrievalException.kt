package uk.co.grahamcox.tcg.authentication.google

/**
 * Exception when an error occurred retrieving the user profile
 * @param message The error message
 */
class UserProfileRetrievalException(message: String = "Failed to retrieve User Profile") : RuntimeException(message)