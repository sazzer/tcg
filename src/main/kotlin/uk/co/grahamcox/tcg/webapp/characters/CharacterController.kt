package uk.co.grahamcox.tcg.webapp.characters

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.characters.*
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.webapp.InvalidRequestFieldException
import uk.co.grahamcox.tcg.webapp.model.characters.CreateCharacterModel

/**
 * Controller for working with Characters
 * @property characterCreator The means to create characters
 */
@RestController
@RequestMapping("/api/characters")
class CharacterController(
        private val characterCreator: CharacterCreator
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(CharacterController::class.java)
    }
    /**
     * Create a new character for the current user
     * @param userId The User ID to create the character for
     * @param character The details of the character to create
     */
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun createCharacter(userId: UserId,
                        @RequestBody character: CreateCharacterModel) {
        LOG.debug("Creating character {} for user {}", character, userId)
        try {
            characterCreator.createCharacter(CharacterTemplate(
                    owner = userId,
                    name = character.name,
                    race = RaceId(character.race),
                    gender = GenderId(character.gender),
                    characterClass = ClassId(character.class_)
            ))
        } catch (e: UnknownRaceException) {
            throw InvalidRequestFieldException(mapOf(
                    "race" to character.race
            ))
        } catch (e: UnknownGenderException) {
            throw InvalidRequestFieldException(mapOf(
                    "gender" to character.gender
            ))
        } catch (e: UnknownClassException) {
            throw InvalidRequestFieldException(mapOf(
                    "class" to character.class_
            ))
        }
        TODO("Not implemented yet")
    }
}