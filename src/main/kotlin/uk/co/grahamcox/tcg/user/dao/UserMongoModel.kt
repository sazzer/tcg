package uk.co.grahamcox.tcg.users.dao

import uk.co.grahamcox.tcg.dao.BaseMongoModel

/**
 * KMongo model for the Users data
 * @property name The name of the user
 * @property email The email address of the user
 * @property providers The IDs of the User at third-party providers
 */
data class UserMongoModel(
        val name: String,
        val email: String?,
        val providers: Map<String, String>?
) : BaseMongoModel()