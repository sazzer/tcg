package uk.co.grahamcox.tcg.user

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.dao.BaseWritableDao
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.NoSort

/**
 * DAO layer for accessing user records
 */
interface UserDao : BaseDao<UserId, UserData, NoFilter, NoSort>, BaseWritableDao<UserId, UserData> {
    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    fun retrieveUserByProviderId(provider: String, providerId: String): Model<UserId, UserData>?
}