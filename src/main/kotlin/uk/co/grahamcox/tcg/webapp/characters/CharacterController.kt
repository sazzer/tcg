package uk.co.grahamcox.tcg.webapp.characters

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.webapp.model.characters.CreateCharacterModel

/**
 * Controller for working with Characters
 */
@RestController
@RequestMapping("/api/characters")
class CharacterController {
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
        TODO("Not implemented yet")
    }
}