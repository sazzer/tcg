package uk.co.grahamcox.tcg.user

/**
 * The data representing a user account
 * @property name The name of the user
 * @property email The email address of the user
 * @property providerIds The map of provider IDs for the user
 */
data class UserData(
        val name: String,
        val email: String?,
        val providerIds: Map<String, String>
)