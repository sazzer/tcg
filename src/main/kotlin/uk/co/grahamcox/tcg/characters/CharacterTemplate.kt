package uk.co.grahamcox.tcg.characters

import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.user.UserId

/**
 * Template used to create a new character
 * @property owner The Owner of the character
 * @property name The name of the character
 * @property race The race of the character
 * @property gender The gender of the character, inside of the race
 * @property characterClass The class of the character
 */
data class CharacterTemplate(
        val owner: UserId,
        val name: String,
        val race: RaceId,
        val gender: GenderId,
        val characterClass: ClassId
)