package uk.co.grahamcox.tcg.authentication.google

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Representation of an email address of the user in the Google Profile
 * @property value The actual email address
 * @property type The type of email address
 */
data class Email @JsonCreator constructor(
        val value: String,
        val type: String
)

/**
 * Representation of the Google User Profile
 * @property id The ID that Google has for the user
 * @property displayName The display name of the user
 * @property emails The email addresses of the user
 * @property verified Whether the account is verified or  not
 */
data class UserProfile @JsonCreator constructor(
        val id: String,
        val displayName: String,
        val emails: List<Email>,
        val verified: Boolean
)