package uk.co.grahamcox.tcg.webapp.cucumber.users

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.DatabaseMatcher

/**
 * Helper to match the user in the database with the expected values
 * @property db The database connection to use
 */
class UserDatabaseMatcher(private val db: MongoDatabase) : DatabaseMatcher(db, "users") {
    /** Mapping of the provided inputs to the fields on the data returned */
    override val userFieldMapping = mapOf(
            "User ID" to "_id",
            "Name" to "name",
            "Email" to "email",
            "Google Provider ID" to "providers/google"
    )
}