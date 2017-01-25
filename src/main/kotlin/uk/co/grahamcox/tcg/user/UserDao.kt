package uk.co.grahamcox.tcg.user

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.Model

/**
 * DAO layer for accessing user records
 */
interface UserDao : BaseDao<UserId, UserData> {
    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    fun retrieveUserByProviderId(provider: String, providerId: String): Model<UserId, UserData>?

    /**
     * Create a new user record with the given user data
     * @param user The user data to persist
     * @return the persisted user model
     */
    fun createUser(user: UserData) : Model<UserId, UserData>

    /**
     * Link the given user to the given Authentication Provider using the given Provider ID
     * @param user The User to link
     * @param provider The provider to link it to
     * @param providerId The ID of the User at the Provider
     */
    fun linkUserToProvider(user: Model<UserId, UserData>, provider: String, providerId: String)
}