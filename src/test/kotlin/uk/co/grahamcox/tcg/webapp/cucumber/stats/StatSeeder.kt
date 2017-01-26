package uk.co.grahamcox.tcg.webapp.cucumber.stats

import org.neo4j.driver.v1.Driver
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.Neo4jSeeder

/**
 * Seeder for seeding Stats records
 */
class StatSeeder(driver: Driver) : Neo4jSeeder(driver) {
    /** The query to execute for the seeding */
    override val createQuery =
            "CREATE (u:Stat {id:{id}, version:{version}, created:{created}, updated: {updated}, name: {name}, description:{description}}) RETURN u"
    /** The mapping between Cucumber fields and query binds */
    override val fieldMapping = mapOf(
            "ID" to "id",
            "Name" to "name",
            "Description" to "description"
    )
    /** The providers for the default field values */
    override val defaultFieldValues = mapOf(
            "name" to { "Test Stat" },
            "description" to { "Some test statistic" }
    )
}