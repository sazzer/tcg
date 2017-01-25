package uk.co.grahamcox.tcg.webapp.users

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.user.UserRetriever
import uk.co.grahamcox.tcg.webapp.api.IdentityModel
import uk.co.grahamcox.tcg.webapp.api.users.UserModel
import java.time.Instant
import java.util.*

/**
 * Controller for accessing user data
 */
@RestController
@RequestMapping("/api/users")
class UserController(private val userRetriever: UserRetriever) {
    /**
     * Get the profile of the current user, based on the access token
     * @param userId The ID of the user to retrieve
     * @return the user
     */
    @RequestMapping("/me")
    fun getCurrentUser(userId: UserId): UserModel {
        val user = userRetriever.retrieveUserById(userId)

        return UserModel()
                .withName(user.data.name)
                .withEmail(user.data.email)
                .withIdentity(IdentityModel()
                        .withId(user.identity.id.id)
                        .withVersion(user.identity.version)
                        .withCreated(user.identity.created)
                        .withUpdated(user.identity.updated)
                )
    }
}