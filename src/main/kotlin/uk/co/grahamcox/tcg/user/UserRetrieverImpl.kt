package uk.co.grahamcox.tcg.user

import uk.co.grahamcox.tcg.model.DaoRetriever

/**
 * Standard implementation of the User Retriever
 * @property dao The User DAO to work with
 */
class UserRetrieverImpl(private val dao: UserDao) : UserRetriever, DaoRetriever<UserId, UserData>(dao) {
    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    override fun retrieveUserByProviderId(provider: String, providerId: String) =
            dao.retrieveUserByProviderId(provider, providerId)
}