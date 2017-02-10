package uk.co.grahamcox.tcg.characters

import uk.co.grahamcox.tcg.model.Model

/**
 * Mechanism to create a new character
 */
interface CharacterCreator {
    /**
     * Create a new character matching the given template
     * @param template The template of the character
     * @return the newly created character
     */
    fun createCharacter(template: CharacterTemplate): Model<CharacterId, CharacterData>
}