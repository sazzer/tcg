package uk.co.grahamcox.tcg.skills.dao

import uk.co.grahamcox.tcg.dao.BaseMongoModel

/**
 * KMongo model for the Skills data
 * @property name The name of the skill
 * @property description The description of the skill
 * @property default The default value of the skill
 */
data class SkillsMongoModel(
        val name: String,
        val description: String,
        val default: Long
) : BaseMongoModel()