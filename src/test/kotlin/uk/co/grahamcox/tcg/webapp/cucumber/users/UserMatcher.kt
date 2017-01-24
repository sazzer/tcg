package uk.co.grahamcox.tcg.webapp.cucumber.users

import com.winterbe.expekt.should
import org.neo4j.driver.v1.Driver
import uk.co.grahamcox.tcg.neo4j.executeStatement

/**
 * Helper to match the user in the database with the expected values
 * @property driver The Neo4J driver to use
 */
class UserMatcher(private val driver: Driver) {
    companion object {
        /** Mapping of the provided inputs to the fieids on the User node */
        private val USER_FIELD_MAPPING = mapOf(
                "User ID" to "id",
                "Name" to "name",
                "Email" to "email"
        )
        /** Mapping of the provided inputs to the fieids on the relationship to the Provider */
        private val PROVIDER_FIELD_MAPPING = mapOf(
                "Google Provider ID" to "id"
        )
    }
    /**
     * Check that the user with the ID provided is in the database with the correct Name and Email
     * @param id The ID to look for
     */
    fun matchUser(userId: String, userData: Map<String, String>) {
        driver.executeStatement("MATCH (u:User {id:{id}})-[r:PROVIDER]->(p:AuthenticationProvider {id:{provider}}) RETURN u, r",
                mapOf(
                        "id" to userId,
                        "provider" to "google"
                )).let { statementResponse ->
            val response = statementResponse.single()
            val user = response.get("u").asNode()
            val providerLink = response.get("r").asRelationship()

            userData.filterKeys { USER_FIELD_MAPPING.containsKey(it) }
                    .mapKeys { USER_FIELD_MAPPING[it.key] }
                    .forEach { field, value ->
                        user.get(field).asString().should.equal(value)
                    }

            userData.filterKeys { PROVIDER_FIELD_MAPPING.containsKey(it) }
                    .mapKeys { PROVIDER_FIELD_MAPPING[it.key] }
                    .forEach { field, value ->
                        providerLink.get(field).asString().should.equal(value)
                    }
        }
    }
}