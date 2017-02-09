package uk.co.grahamcox.tcg.genders.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * KMongo model for the Genders data
 * @property id The ID of the gender
 * @property version The version of the gender
 * @property created When the gender was created
 * @property updated When the gender was last updated
 * @property name The name of the gender
 * @property description The description of the gender
 * @property race The ID of the Race this Gender is for
 * @property attributes The attributes granted by this gender
 * @property skills The skills granted by this gender
 * @property abilities The abilities granted by this gender
 */
data class GendersMongoModel(
        @MongoId
        @JsonProperty("_id")
        val id: String,
        val version: String,
        val created: Instant,
        val updated: Instant,
        val name: String,
        val description: String,
        val race: String,
        val attributes: Map<String, Long>?,
        val skills: Map<String, Long>?,
        val abilities: List<String>?
)