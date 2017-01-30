package uk.co.grahamcox.tcg.webapp.cucumber.users

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.SimpleFieldDefaulter

/**
 * Mechanism by which we can seed user records
 * @property db The MongoDB connection
 */
class UserSeeder(private val db: MongoDatabase) : MongoSeeder(db,
        "users",
        mapOf(
                "User ID" to "_id",
                "Name" to "name",
                "Email" to "email"
        ),
        mapOf(
                "name" to SimpleFieldDefaulter("Test User"),
                "email" to SimpleFieldDefaulter("test@example.com")
        )) {

    /** The more complex mappings, e.g. for nested fields */
    override val complexFieldMapping = mapOf(
            "Google Provider ID" to ProviderIdFieldMapper("google")
    )
}