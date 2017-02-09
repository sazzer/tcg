package uk.co.grahamcox.tcg.classes.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * KMongo model for the Classes data
 * @property id The ID of the class
 * @property version The version of the class
 * @property created When the class was created
 * @property updated When the class was last updated
 * @property name The name of the class
 * @property description The description of the class
 * @property attributes The attributes granted by this class
 * @property skills The skills granted by this class
 * @property abilities The abilities granted by this class
 */
data class ClassesMongoModel(
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