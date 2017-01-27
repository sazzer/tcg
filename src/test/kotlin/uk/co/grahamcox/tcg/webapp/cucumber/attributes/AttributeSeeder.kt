package uk.co.grahamcox.tcg.webapp.cucumber.attributes

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder

/**
 * Seeder for seeding Attribute records
 */
class AttributeSeeder(database: MongoDatabase) : MongoSeeder(database, "attributes") {
    /** The mapping between Cucumber fields and query binds */
    override val fieldMapping = mapOf(
            "ID" to "_id",
            "Name" to "name",
            "Description" to "description"
    )
    /** The providers for the default field values */
    override val defaultFieldValues = mapOf(
            "name" to { "Test Attribute" },
            "description" to { "Some test attribute" }
    )
}