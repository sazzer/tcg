package uk.co.grahamcox.tcg.user

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.UnknownResourceException

/**
 * Mechanism to retrieve a user
 */
interface UserRetriever {
    /**
     * Retrieve a user by it's internal ID
     * @param id The ID of the user
     * @return the user
     * @throws UnknownResourceException if the user doesn't exist
     */
    fun retrieveUserById(id: UserId) : UserModel

    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    fun retrieveUserByProviderId(provider: String, providerId: String): UserModel?
}