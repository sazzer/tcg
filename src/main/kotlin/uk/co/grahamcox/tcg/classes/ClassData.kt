package uk.co.grahamcox.tcg.classes

import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.skills.SkillId

/**
 * Representation of a single class
 * @property name The name of the class
 * @property description The description of the class
 * @property attributeModifiers The attribute modifiers to apply based on this class
 * @property skillModifiers The skill modifiers to apply based on this class
 * @property grantedAbilities The abilities to grant based on this class
 */
data class ClassData(
        val name: String,
        val description: String,
        val attributeModifiers: Map<AttributeId, Long>,
        val skillModifiers: Map<SkillId, Long>,
        val grantedAbilities: Set<AbilityId>
)