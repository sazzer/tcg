package uk.co.grahamcox.tcg.attributes.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * KMongo model for the Attributes data
 * @property id The ID of the attribute
 * @property version The version of the attribute
 * @property created When the attribute was created
 * @property updated When the attribute was last updated
 * @property name The name of the attribute
 * @property description The description of the attribute
 * @property default The default value of the attribute
 */
data class AttributesMongoModel(
        @MongoId
        @JsonProperty("_id")
        val id: String,
        val version: String,
        val created: Instant,
        val updated: Instant,
        val name: String,
        val description: String,
        val default: Long
)