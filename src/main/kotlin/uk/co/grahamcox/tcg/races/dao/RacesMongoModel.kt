package uk.co.grahamcox.tcg.races.dao

import uk.co.grahamcox.tcg.dao.BaseMongoModel

/**
 * KMongo model for the Races data
 * @property name The name of the race
 * @property description The description of the race
 * @property attributes The attributes granted by this race
 * @property skills The skills granted by this race
 * @property abilities The abilities granted by this race
 */
data class RacesMongoModel(
        val name: String,
        val description: String,
        val attributes: Map<String, Long>?,
        val skills: Map<String, Long>?,
        val abilities: List<String>?
) : BaseMongoModel()