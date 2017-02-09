package uk.co.grahamcox.tcg.abilities.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * KMongo model for the Abilities data
 * @property id The ID of the Ability
 * @property version The version of the Ability
 * @property created When the Ability was created
 * @property updated When the Ability was last updated
 * @property name The name of the ability
 * @property description The description of the ability
 */
data class AbilitiesMongoModel(
        @MongoId
        @JsonProperty("_id")
        val id: String,
        val version: String,
        val created: Instant,
        val updated: Instant,
        val name: String,
        val description: String
)