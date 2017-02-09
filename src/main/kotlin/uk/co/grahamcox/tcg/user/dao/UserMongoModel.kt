package uk.co.grahamcox.tcg.users.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * KMongo model for the Users data
 * @property id The ID of the user
 * @property version The version of the user
 * @property created When the user was created
 * @property updated When the user was last updated
 * @property name The name of the user
 * @property email The email address of the user
 * @property providers The IDs of the User at third-party providers
 */
data class UserMongoModel(
        @MongoId
        @JsonProperty("_id")
        val id: String,
        val version: String,
        val created: Instant,
        val updated: Instant,
        val name: String,
        val email: String?,
        val providers: Map<String, String>?
)