package uk.co.grahamcox.tcg.user

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoSort
import java.time.Clock

/**
 * Implementation of the User DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class UserDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock) : UserDao, BaseMongoDao<UserId, UserData, NoSort>(db, "users", clock) {
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
    override fun parseResult(result: Document): Model<UserId, UserData> {
        return Model(
                identity = Identity(
                        id = UserId(result.getString("_id")),
                        version = result.getString("version"),
                        created = result.getDate("created").toInstant(),
                        updated = result.getDate("updated").toInstant()
                ),
                data = UserData(
                        name = result.getString("name"),
                        email = result.getString("email"),
                        providers = result.get("providers", Map::class.java)
                                ?.mapKeys { it.key as String }
                                ?.mapValues { it.value as String }
                                ?: mapOf()
                )
        )
    }

    /**
     * Build the document that we will store in the database. Note that this only needs to worry about the data fields
     * as everything else is taken care of automatically
     * @param input The input data
     * @return the document
     */
    override fun buildDocument(input: UserData): Document {
        return Document(mapOf(
                "name" to input.name,
                "email" to input.email,
                "providers" to input.providers
        ))
    }
}