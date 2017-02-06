package uk.co.grahamcox.tcg.races

import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.skills.SkillId

/**
 * Representation of a single race
 * @property name The name of the race
 * @property description The description of the race
 * @property attributeModifiers The attribute modifiers to apply based on this race
 * @property skillModifiers The skill modifiers to apply based on this race
 * @property grantedAbilities The abilities to grant based on this race
 */
data class RaceData(
        val name: String,
        val description: String,
        val attributeModifiers: Map<AttributeId, Long>,
        val skillModifiers: Map<SkillId, Long>,
        val grantedAbilities: Set<AbilityId>
)