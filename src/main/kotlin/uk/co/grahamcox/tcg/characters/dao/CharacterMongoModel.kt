package uk.co.grahamcox.tcg.characters.dao

import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.grahamcox.tcg.dao.BaseMongoModel

/**
 * KMongo model for the Character data
 * @property owner The owner of the character
 * @property name The name of the character
 * @property race The race of the character
 * @property gender The gender of the character
 * @property characterClass The class of the character
 * @property attributes The attributes of the character
 * @property skills The skills of the character
 * @property abilities The abilities of the character
 */
data class CharacterMongoModel(
        val owner: String,
        val name: String,
        val race: String,
        val gender: String,
        @JsonProperty("class")
        val characterClass: String,
        val attributes: Map<String, Long>,
        val skills: Map<String, Long>,
        val abilities: List<String>
) : BaseMongoModel()