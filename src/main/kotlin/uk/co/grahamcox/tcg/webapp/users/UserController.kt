package uk.co.grahamcox.tcg.webapp.users

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.user.UserRetriever
import uk.co.grahamcox.tcg.webapp.api.Resource
import uk.co.grahamcox.tcg.webapp.api.translator.ResponseEntityResourceTranslatorWrapper

/**
 * Controller for accessing user data
 */
@RestController
@RequestMapping("/api/users")
class UserController(
        private val userRetriever: UserRetriever,
        private val resourceTranslator: ResponseEntityResourceTranslatorWrapper<UserId, UserData, String, UserResourceData>) {

    /**
     * Get the profile of the given user, based on the access token
     * @param userId The ID of the user to retrieve
     * @return the user
     */
    @RequestMapping("/{id}")
    fun getUser(@PathVariable("id") userId: String): ResponseEntity<Resource<String, UserResourceData>> {
        val user = userRetriever.retrieveById(UserId(userId))

        return resourceTranslator.translate(user)
    }

    /**
     * Get the profile of the current user, based on the access token
     * @param userId The ID of the user to retrieve
     * @return the user
     */
    @RequestMapping("/me")
    fun getCurrentUser(userId: UserId): ResponseEntity<Resource<String, UserResourceData>> {
        val user = userRetriever.retrieveById(userId)

        return resourceTranslator.translate(user)
    }
}