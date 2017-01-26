package uk.co.grahamcox.tcg.webapp.cucumber.seeder

import org.neo4j.driver.v1.AccessMode
import org.neo4j.driver.v1.Driver
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.neo4j.executeStatement
import java.time.Instant
import java.util.*

/**
 * Base class for simple seeders for Neo4J records
 * @property neo4j The Neo4J connection
 */
abstract class Neo4jSeeder(private val neo4j: Driver) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(Neo4jSeeder::class.java)
    }

    /** The query to execute for the seeding */
    protected abstract val createQuery: String
    /** The mapping between Cucumber fields and query binds */
    protected abstract val fieldMapping: Map<String, String>
    /** The providers for the default field values */
    protected abstract val defaultFieldValues: Map<String, () -> Any>
    /** The providers for the default field values for the identity of the record */
    protected open val defaultIdentityFieldValues = mapOf(
            "id" to { UUID.randomUUID().toString() },
            "version" to { UUID.randomUUID().toString() },
            "created" to { Instant.now().toEpochMilli() },
            "updated" to { Instant.now().toEpochMilli() }
    )

    /**
     * Actually seed the required value into the database
     * @param details The details to seed into the database
     */
    fun seed(details: Map<String, String>) {
        val params = mutableMapOf<String, Any>()
        defaultIdentityFieldValues.mapValues { it.value.invoke() }
                .forEach { k, v -> params[k] = v }
        defaultFieldValues.mapValues { it.value.invoke() }
                .forEach { k, v -> params[k] = v }
        details.filterKeys { fieldMapping.containsKey(it) }
                .mapKeys { fieldMapping[it.key]!! }
                .forEach { k, v -> params[k] = v }
        LOG.debug("Seeding data with query {} and params {}", createQuery, params)
        neo4j.executeStatement(createQuery, params, AccessMode.WRITE)
    }
}