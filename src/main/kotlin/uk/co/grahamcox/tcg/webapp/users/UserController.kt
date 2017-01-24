package uk.co.grahamcox.tcg.webapp.users

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.webapp.api.IdentityModel
import uk.co.grahamcox.tcg.webapp.api.users.UserModel
import java.time.Instant
import java.util.*

/**
 * Controller for accessing user data
 */
@RestController
@RequestMapping("/api/users")
class UserController {
    /**
     * Get the profile of the current user, based on the access token
     * @param userId The ID of the user to retrieve
     * @return the user
     */
    @RequestMapping("/me")
    fun getCurrentUser(userId: UserId): UserModel {
        return UserModel()
                .withName("Test User")
                .withEmail("test@example.com")
                .withIdentity(IdentityModel()
                        .withId(userId.id)
                        .withVersion(UUID.randomUUID().toString())
                        .withCreated(Instant.now())
                        .withUpdated(Instant.now())
                )
    }
}