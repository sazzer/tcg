package uk.co.grahamcox.tcg.webapp.cucumber.attributes

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.SimpleFieldDefaulter

/**
 * Seeder for seeding Attribute records
 */
class AttributeSeeder(database: MongoDatabase) : MongoSeeder(database,
        "attributes",
        mapOf(
                "ID" to "_id",
                "Name" to "name",
                "Description" to "description"
        ),
        mapOf(
                "name" to SimpleFieldDefaulter("Test Attribute"),
                "description" to SimpleFieldDefaulter("Some test attribute")
        )) {
}