package uk.co.grahamcox.tcg.skills.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * KMongo model for the Skills data
 * @property id The ID of the skill
 * @property version The version of the skill
 * @property created When the skill was created
 * @property updated When the skill was last updated
 * @property name The name of the skill
 * @property description The description of the skill
 * @property default The default value of the skill
 */
data class SkillsMongoModel(
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