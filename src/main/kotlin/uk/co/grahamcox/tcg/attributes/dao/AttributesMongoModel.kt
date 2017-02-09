package uk.co.grahamcox.tcg.attributes.dao

import uk.co.grahamcox.tcg.dao.BaseMongoModel

/**
 * KMongo model for the Attributes data
 * @property name The name of the attribute
 * @property description The description of the attribute
 * @property default The default value of the attribute
 */
data class AttributesMongoModel(
        val name: String,
        val description: String,
        val default: Long
) : BaseMongoModel()