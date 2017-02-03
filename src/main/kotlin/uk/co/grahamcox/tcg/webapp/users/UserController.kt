package uk.co.grahamcox.tcg.webapp.users

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.user.UserRetriever
import uk.co.grahamcox.tcg.webapp.ResponseTranslator
import uk.co.grahamcox.tcg.webapp.model.UserModel

/**
 * Controller for accessing user data
 * @property userRetriever The means to retrieve users
 * @property modelTranslator The translator to use for the individual models
 */
@RestController
@RequestMapping("/api/users")
class UserController(
        private val userRetriever: UserRetriever,
        private val modelTranslator: ResponseTranslator<UserId, UserData, UserModel>) {
    /**
     * Get the profile of the current user, based on the access token
     * @param userId The ID of the user to retrieve
     * @return the user
     */
    @RequestMapping("/me")
    fun getCurrentUser(userId: UserId): ResponseEntity<UserModel> {
        val user = userRetriever.retrieveById(userId)

        return modelTranslator.translate(user)
    }
}