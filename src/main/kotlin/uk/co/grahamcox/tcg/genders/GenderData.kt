package uk.co.grahamcox.tcg.genders

import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.skills.SkillId

/**
 * Representation of a single gender
 * @property name The name of the gender
 * @property description The description of the gender
 * @property race The Race that the Gender relates to
 * @property attributeModifiers The attribute modifiers to apply based on this race
 * @property skillModifiers The skill modifiers to apply based on this race
 * @property grantedAbilities The abilities to grant based on this race
 */
data class GenderData(
        val name: String,
        val description: String,
        val race: RaceId,
        val attributeModifiers: Map<AttributeId, Long>,
        val skillModifiers: Map<SkillId, Long>,
        val grantedAbilities: Set<AbilityId>
)