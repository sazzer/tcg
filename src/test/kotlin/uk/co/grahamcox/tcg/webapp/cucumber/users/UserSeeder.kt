package uk.co.grahamcox.tcg.webapp.cucumber.users

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder

/**
 * Mechanism by which we can seed user records
 * @property neo4j The Neo4J connection
 */
class UserSeeder(private val db: MongoDatabase) : MongoSeeder(db, "users") {

    /** The mapping between Cucumber fields and Mongo Fields */
    override val fieldMapping = mapOf(
            "User ID" to "_id",
            "Name" to "name",
            "Email" to "email"
    )
    /** The more complex mappings, e.g. for nested fields */
    override val complexFieldMapping = mapOf(
            "Google Provider ID" to { field: String, document: MutableMap<String, Any> ->
                val providers =
                        document.getOrPut("providers", { mutableMapOf<String, String>() }) as MutableMap<String, String>
                providers["google"] = field
            }
    )
    /** The providers for the default field values */
    override val defaultFieldValues = mapOf(
            "name" to { "Test User" },
            "email" to { "test@example.com" }
    )
}