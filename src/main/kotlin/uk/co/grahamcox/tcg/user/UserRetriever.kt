package uk.co.grahamcox.tcg.user

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoSort
import uk.co.grahamcox.tcg.model.Retriever

/**
 * Mechanism to retrieve a user
 */
interface UserRetriever : Retriever<UserId, UserData, NoSort> {
    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    fun retrieveUserByProviderId(provider: String, providerId: String): Model<UserId, UserData>?
}