package uk.co.grahamcox.tcg.webapp.cucumber.stats

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder

/**
 * Seeder for seeding Stats records
 */
class StatSeeder(database: MongoDatabase) : MongoSeeder(database, "stat") {
    /** The mapping between Cucumber fields and query binds */
    override val fieldMapping = mapOf(
            "ID" to "_id",
            "Name" to "name",
            "Description" to "description"
    )
    /** The providers for the default field values */
    override val defaultFieldValues = mapOf(
            "name" to { "Test Stat" },
            "description" to { "Some test statistic" }
    )
}