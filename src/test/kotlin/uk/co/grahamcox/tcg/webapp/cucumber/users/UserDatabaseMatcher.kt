package uk.co.grahamcox.tcg.webapp.cucumber.users

import com.winterbe.expekt.should
import org.apache.commons.jxpath.JXPathContext
import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.Value
import uk.co.grahamcox.tcg.neo4j.executeStatement

/**
 * Helper to match the user in the database with the expected values
 * @property driver The Neo4J driver to use
 */
class UserDatabaseMatcher(private val driver: Driver) {
    companion object {
        /** Mapping of the provided inputs to the fields on the User data returned */
        private val USER_FIELD_MAPPING = mapOf(
                "User ID" to "u/id",
                "Name" to "u/name",
                "Email" to "u/email",
                "Google Provider ID" to "r/id"
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

            val responseData = response.fields()
                    .map { it.key() to it.value() }
                    .toMap()
                    .mapValues { it.value.asMap() }

            val jxPathContext = JXPathContext.newContext(responseData)

            userData.filterKeys { USER_FIELD_MAPPING.containsKey(it) }
                    .mapKeys { USER_FIELD_MAPPING[it.key] }
                    .forEach { field, value ->
                        jxPathContext.getValue(field).should.equal(value)
                    }
        }
    }
}