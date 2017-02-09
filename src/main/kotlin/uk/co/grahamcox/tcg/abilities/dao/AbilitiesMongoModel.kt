package uk.co.grahamcox.tcg.abilities.dao

import uk.co.grahamcox.tcg.dao.BaseMongoModel

/**
 * KMongo model for the Abilities data
 * @property name The name of the ability
 * @property description The description of the ability
 */
data class AbilitiesMongoModel(
        val name: String,
        val description: String
) : BaseMongoModel()