package uk.co.grahamcox.tcg.webapp.acceptance.authentication

import com.winterbe.expekt.should
import org.neo4j.driver.v1.Driver
import uk.co.grahamcox.tcg.neo4j.executeStatement

/**
 * Helper to match the user in the database with the expected values
 * @property driver The Neo4J driver to use
 */
class UserMatcher(private val driver: Driver) {
    /**
     * Check that the user with the ID provided is in the database with the correct Name and Email
     * @param id The ID to look for
     * @param name The name to check
     * @param email The email to check
     */
    fun matchUser(id: String, name: String, email: String) {

        driver.executeStatement("MATCH (u:User {id:{id}}) RETURN u", mapOf("id" to id)).let { statementResponse ->
            val userRecord = statementResponse.single().get("u").asNode()
            userRecord.get("name").asString().should.equal(name)
            userRecord.get("email").asString().should.equal(email)
        }
    }
}