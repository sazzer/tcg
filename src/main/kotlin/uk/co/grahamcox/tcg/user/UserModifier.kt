package uk.co.grahamcox.tcg.user

/**
 * Mechanism to modify user records
 */
interface UserModifier {
    /**
     * Create a new user record with the given user data
     * @param user The user data to persist
     * @return the persisted user model
     */
    fun createUser(user: UserData) : UserModel
}