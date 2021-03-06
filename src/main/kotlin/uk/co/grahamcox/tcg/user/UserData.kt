package uk.co.grahamcox.tcg.user

/**
 * The data representing a user account
 * @property name The name of the user
 * @property email The email address of the user
 * @property providers The providers that can authenticate this user
 */
data class UserData(
        val name: String,
        val email: String?,
        val providers: Map<String, String>
)