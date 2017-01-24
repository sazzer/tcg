package uk.co.grahamcox.tcg.webapp.cucumber.users

import org.neo4j.driver.v1.Driver
import uk.co.grahamcox.tcg.neo4j.execute
import uk.co.grahamcox.tcg.neo4j.executeStatement
import java.time.Instant
import java.util.*

/**
 * Mechanism by which we can seed user records
 * @property neo4j The Neo4J connection
 */
class UserSeeder(private val neo4j: Driver) {
    companion object {
        /** Input mappings for user fields */
        private val USER_FIELD_MAPPING = mapOf(
                "User ID" to "id",
                "Name" to "name",
                "Email" to "email"
        )
        /** Default values for user fields */
        private val USER_FIELD_DEFAILTS = mapOf(
                "id" to { UUID.randomUUID().toString() },
                "version" to { UUID.randomUUID().toString() },
                "created" to { Instant.now().toEpochMilli() },
                "updated" to { Instant.now().toEpochMilli() },
                "name" to { "Test User" },
                "email" to { "test@example.com" }
        )

        /** Input mappings for the relationship to the Google provider */
        private val GOOGLE_PROVIDER_FIELD_MAPPING = mapOf(
                "User ID" to "id",
                "Google Provider ID" to "providerId"
        )
    }
    /**
     * Seed a user with the following details
     */
    fun seedUser(userDetails: Map<String, String>) {
        neo4j.execute { session ->
            val userParams = mutableMapOf<String, Any>()
            USER_FIELD_DEFAILTS.mapValues { it.value.invoke() }
                    .forEach { k, v -> userParams[k] = v }
            userDetails.filterKeys { USER_FIELD_MAPPING.containsKey(it) }
                    .mapKeys { USER_FIELD_MAPPING[it.key]!! }
                    .forEach { k, v -> userParams[k] = v }
            session.run("CREATE (u:User {id:{id}, version:{version}, created:{created}, updated: {updated}, name: {name}, email:{email}}) RETURN u",
                    userParams)
            session.run("MERGE (p:AuthenticationProvider {id:{id}})", mapOf("id" to "google"))

            val relationshipParams = mutableMapOf<String, Any>()
            userDetails.filterKeys { GOOGLE_PROVIDER_FIELD_MAPPING.containsKey(it) }
                    .mapKeys { GOOGLE_PROVIDER_FIELD_MAPPING[it.key]!! }
                    .forEach { k, v -> relationshipParams[k] = v }
            if (relationshipParams.size == GOOGLE_PROVIDER_FIELD_MAPPING.size) {
                relationshipParams.put("provider", "google")
                session.run("MATCH (u:User {id:{id}}), (p:AuthenticationProvider {id:{provider}}) CREATE (u)-[:PROVIDER {id:{providerId}}]->(p)",
                        relationshipParams)
            }

        }
    }

}