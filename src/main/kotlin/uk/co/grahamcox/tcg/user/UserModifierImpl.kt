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
}