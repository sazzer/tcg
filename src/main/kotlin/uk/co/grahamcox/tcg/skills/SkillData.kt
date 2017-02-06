package uk.co.grahamcox.tcg.skills

/**
 * Representation of a single skill
 * @property name The name of the skill
 * @property description The description of the skill
 * @property defaultValue The default value of this skill, for new characters
 */
data class SkillData(
        val name: String,
        val description: String,
        val defaultValue: Long
)