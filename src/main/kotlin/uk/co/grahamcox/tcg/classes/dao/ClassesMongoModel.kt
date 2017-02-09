package uk.co.grahamcox.tcg.classes.dao

import uk.co.grahamcox.tcg.dao.BaseMongoModel

/**
 * KMongo model for the Classes data
 * @property name The name of the class
 * @property description The description of the class
 * @property attributes The attributes granted by this class
 * @property skills The skills granted by this class
 * @property abilities The abilities granted by this class
 */
data class ClassesMongoModel(
        val name: String,
        val description: String,
        val attributes: Map<String, Long>?,
        val skills: Map<String, Long>?,
        val abilities: List<String>?
) : BaseMongoModel()