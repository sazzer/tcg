package uk.co.grahamcox.tcg.attributes

import uk.co.grahamcox.tcg.dao.MongoModel

/**
 * KMongo model for the Attributes data
 * @property name The name of the attribute
 * @property description The description of the attribute
 * @property default The default value of the attribute
 */
data class AttributesMongoModel(
        var name: String,
        var description: String,
        var default: Long
) : MongoModel()