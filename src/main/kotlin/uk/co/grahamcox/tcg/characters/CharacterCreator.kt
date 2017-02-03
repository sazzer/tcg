package uk.co.grahamcox.tcg.characters

/**
 * Mechanism to create a new character
 */
interface CharacterCreator {
    /**
     * Create a new character matching the given template
     * @param template The template of the character
     */
    fun createCharacter(template: CharacterTemplate)
}