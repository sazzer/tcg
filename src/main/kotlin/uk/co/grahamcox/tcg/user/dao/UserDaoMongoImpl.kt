package uk.co.grahamcox.tcg.user.dao

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.dao.BaseKMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.NoSort
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import java.time.Clock

/**
 * Implementation of the User DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class UserDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock) : UserDao, BaseKMongoDao<UserId, UserData, UserMongoModel, NoFilter, NoSort>(db,
        "users",
        clock,
        UserMongoModel::class.java) {
    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    override fun retrieveUserByProviderId(provider: String, providerId: String): Model<UserId, UserData>? {
        val query = BasicDBObject()
                .append("providers.$provider", providerId)
        return loadOneWithQuery(query)
    }

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: UserMongoModel): Model<UserId, UserData> {
        return Model(
                identity = Identity(
                        id = UserId(result.id),
                        version = result.version,
                        created = result.created,
                        updated = result.updated

                ),
                data = UserData(
                        name = result.name,
                        email = result.email,
                        providers = result.providers ?: mapOf()
                )
        )
    }

    /**
     * Build a document from the provided record
     * @param data The data to build the document from
     * @return the document to save into the database
     */
    override fun buildDocument(data: UserData) = UserMongoModel(
            name = data.name,
            email = data.email,
            providers = data.providers
    )
}