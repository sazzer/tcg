package uk.co.grahamcox.tcg.races.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * KMongo model for the Races data
 * @property id The ID of the race
 * @property version The version of the race
 * @property created When the race was created
 * @property updated When the race was last updated
 * @property name The name of the race
 * @property description The description of the race
 * @property attributes The attributes granted by this race
 * @property skills The skills granted by this race
 * @property abilities The abilities granted by this race
 */
data class RacesMongoModel(
        @MongoId
        @JsonProperty("_id")
        val id: String,
        val version: String,
        val created: Instant,
        val updated: Instant,
        val name: String,
        val description: String,
        val attributes: Map<String, Long>?,
        val skills: Map<String, Long>?,
        val abilities: List<String>?
)