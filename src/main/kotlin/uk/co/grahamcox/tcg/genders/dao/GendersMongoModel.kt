package uk.co.grahamcox.tcg.genders.dao

import uk.co.grahamcox.tcg.dao.BaseMongoModel

/**
 * KMongo model for the Genders data
 * @property name The name of the gender
 * @property description The description of the gender
 * @property race The ID of the Race this Gender is for
 * @property attributes The attributes granted by this gender
 * @property skills The skills granted by this gender
 * @property abilities The abilities granted by this gender
 */
data class GendersMongoModel(
        val name: String,
        val description: String,
        val race: String,
        val attributes: Map<String, Long>?,
        val skills: Map<String, Long>?,
        val abilities: List<String>?
) : BaseMongoModel()