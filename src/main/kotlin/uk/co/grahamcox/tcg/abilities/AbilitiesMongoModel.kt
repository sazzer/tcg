package uk.co.grahamcox.tcg.abilities

import uk.co.grahamcox.tcg.dao.MongoModel

/**
 * KMongo model for the Abilities data
 * @property name The name of the ability
 * @property description The description of the ability
 */
data class AbilitiesMongoModel(
        var name: String,
        var description: String
) : MongoModel()