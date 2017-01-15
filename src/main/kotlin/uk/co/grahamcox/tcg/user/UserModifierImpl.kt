package uk.co.grahamcox.tcg.user

/**
 * Standard implementation of the User Modifier
 * @property dao The User DAO to work with
 */
class UserModifierImpl(private val dao: UserDao) : UserModifier {
    /**
     * Create a new user record with the given user data
     * @param user The user data to persist
     * @return the persisted user model
     */
    override fun createUser(user: UserData) = dao.createUser(user)

    /**
     * Create a new user record with the given user data, and link it to a Provider
     * @param user The user data to persist
     * @param provider The provider to link it to
     * @param providerId The ID of the User at the Provider
     * @return the persisted user model
     */
    override fun createUser(user: UserData, provider: String, providerId: String): UserModel {
        val userModel = createUser(user)
        linkUserToProvider(userModel, provider, providerId)
        return userModel
    }

    /**
     * Link the given user to the given Authentication Provider using the given Provider ID
     * @param user The User to link
     * @param provider The provider to link it to
     * @param providerId The ID of the User at the Provider
     */
    override fun linkUserToProvider(user: UserModel, provider: String, providerId: String) {
        dao.linkUserToProvider(user, provider, providerId)
    }
}