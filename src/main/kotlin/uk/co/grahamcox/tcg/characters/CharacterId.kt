package uk.co.grahamcox.tcg.characters

import uk.co.grahamcox.tcg.model.Id

/**
 * ID of a Character
 * @property id The actual ID
 */
data class CharacterId(override val id: String) : Id