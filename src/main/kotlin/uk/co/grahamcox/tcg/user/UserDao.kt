package uk.co.grahamcox.tcg.user

/**
 * DAO layer for accessing user records
 */
interface UserDao {
    /**
     * Retrieve a user by it's internal ID
     * @param id The ID of the user
     * @return the user, or null if no user with this internal ID is present
     */
    fun retrieveUserById(id: UserId) : UserModel?

    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    fun retrieveUserByProviderId(provider: String, providerId: String): UserModel?

    /**
     * Create a new user record with the given user data
     * @param user The user data to persist
     * @return the persisted user model
     */
    fun createUser(user: UserData) : UserModel

    /**
     * Link the given user to the given Authentication Provider using the given Provider ID
     * @param user The User to link
     * @param provider The provider to link it to
     * @param providerId The ID of the User at the Provider
     */
    fun linkUserToProvider(user: UserModel, provider: String, providerId: String)
}