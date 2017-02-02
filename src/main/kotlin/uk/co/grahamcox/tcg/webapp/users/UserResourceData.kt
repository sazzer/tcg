package uk.co.grahamcox.tcg.webapp.users

/**
 * Resource data to represent an User
 * @property name the name of the user
 * @property email the email address of the user
 */
data class UserResourceData(
        val name: String,
        val email: String?
)