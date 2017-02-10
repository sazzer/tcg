package uk.co.grahamcox.tcg.characters

import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.skills.SkillId
import uk.co.grahamcox.tcg.user.UserId

/**
 * Representation of a Character
 * @property owner The owner of the character
 * @property name The name of the character
 * @property race The race of the character
 * @property gender The gender of the character
 * @property characterClass The class of the character
 * @property attributes The attributes of the character
 * @property skills The skills of the character
 * @property abilities The abilities of the character
 */
data class CharacterData(
        val owner: UserId,
        val name: String,
        val race: RaceId,
        val gender: GenderId,
        val characterClass: ClassId,
        val attributes: Map<AttributeId, Long>,
        val skills: Map<SkillId, Long>,
        val abilities: Set<AbilityId>
)