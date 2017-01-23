package uk.co.grahamcox.tcg.webapp.acceptance.authentication

import org.neo4j.driver.v1.Driver
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.neo4j.execute
import java.util.*

/**
 * Mechanism to create user records
 * @property driver The Neo4J Driver
 */
class UserSeeder(private val driver: Driver) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(UserSeeder::class.java)
    }

    /**
     * Helper to create a user in the database
     * @param name The name of the user
     * @param email The email address of the user
     * @param providerIds The mapping of provider to provider ID
     * @return the Internal ID of the user
     */
    fun createUser(name: String = "Test User",
                   email: String = "test@example.com",
                   providerIds: Map<String, String> = mapOf()): String {
        return driver.execute { statement ->
            val id = UUID.randomUUID().toString()
            statement.run("CREATE (u:User {id:{id}, version:{version}, created:{created}, updated:{updated}, name:{name}, email:{email}})",
                    mapOf(
                            "id" to id,
                            "version" to UUID.randomUUID().toString(),
                            "created" to 0,
                            "updated" to 0,
                            "name" to name,
                            "email" to email
                    ))
            LOG.debug("Created user with name {}: {}", name, id)

            providerIds.forEach { provider, providerId ->
                statement.run("CREATE (p:AuthenticationProvider {id:{provider}})",
                        mapOf(
                                "provider" to provider
                        ))
                statement.run("MATCH (p:AuthenticationProvider {id:{provider}}), (u:User {id:{user}}) CREATE (u)-[:PROVIDER {id:{providerId}}]->(p)",
                        mapOf(
                                "provider" to provider,
                                "user" to id,
                                "providerId" to providerId
                        ))
                LOG.debug("User ID has Provider ID {} for Provider {}", id, providerId, provider)
            }

            id
        }
    }
}