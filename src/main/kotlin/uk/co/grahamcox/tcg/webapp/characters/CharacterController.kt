package uk.co.grahamcox.tcg.webapp.characters

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.classes.ClassSort
import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.genders.GenderSort
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.model.UnknownResourceException
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.races.RaceSort
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.webapp.InvalidRequestFieldException
import uk.co.grahamcox.tcg.webapp.model.characters.CreateCharacterModel

/**
 * Controller for working with Characters
 */
@RestController
@RequestMapping("/api/characters")
class CharacterController(
        private val racesRetriever: Retriever<RaceId, RaceData, NoFilter, RaceSort>,
        private val gendersRetriever: Retriever<GenderId, GenderData, NoFilter, GenderSort>,
        private val classsRetriever: Retriever<ClassId, ClassData, NoFilter, ClassSort>) {
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
        var race = try {
            racesRetriever.retrieveById(RaceId(character.race))
        } catch (e: UnknownResourceException) {
            throw InvalidRequestFieldException(mapOf("race" to character.race))
        }
        var gender = try {
            gendersRetriever.retrieveById(GenderId(character.gender))
        } catch (e: UnknownResourceException) {
            throw InvalidRequestFieldException(mapOf("gender" to character.gender))
        }
        var cls = try {
            classsRetriever.retrieveById(ClassId(character.class_))
        } catch (e: UnknownResourceException) {
            throw InvalidRequestFieldException(mapOf("class" to character.class_))
        }

        TODO("Not implemented yet")
    }
}