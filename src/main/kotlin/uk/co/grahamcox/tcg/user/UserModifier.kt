package uk.co.grahamcox.tcg.user

/**
 * Mechanism to modify user records
 */
interface UserModifier {
    /**
     * Create a new user record with the given user data, and link it to a Provider
     * @param user The user data to persist
     * @param provider The provider to link it to
     * @param providerId The ID of the User at the Provider
     * @return the persisted user model
     */
    fun createUser(user: UserData, provider: String, providerId: String) : UserModel

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