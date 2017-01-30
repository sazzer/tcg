package uk.co.grahamcox.tcg.webapp.cucumber.attributes

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.SimpleFieldDefaulter

/**
 * Seeder for seeding Attribute records
 */
class AttributeSeeder(database: MongoDatabase) : MongoSeeder(database, "attributes", mapOf(
        "name" to SimpleFieldDefaulter("Test Attribute"),
        "description" to SimpleFieldDefaulter("Some test attribute")
)) {
    /** The mapping between Cucumber fields and query binds */
    override val fieldMapping = mapOf(
            "ID" to "_id",
            "Name" to "name",
            "Description" to "description"
    )
}