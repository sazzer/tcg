package uk.co.grahamcox.tcg.webapp.acceptance

import com.jayway.jsonpath.JsonPath
import com.winterbe.expekt.should
import org.junit.Test
import org.neo4j.driver.v1.AccessMode
import org.neo4j.driver.v1.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import uk.co.grahamcox.tcg.neo4j.executeStatement
import java.util.*

/**
 * Acceptance Test for the Healthchecks
 */
class HealthcheckIT : AcceptanceTestBase() {
    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    /** The database driver. Note - don't do this */
    @Autowired
    private lateinit var driver: Driver

    @Test
    fun `Checking the healthchecks with an empty database`() {
        requester.get("/health").let { response ->
            response.statusCode.should.equal(HttpStatus.OK)
            JsonPath.read<String>(response.body, "$.status").should.equal("UP")
            JsonPath.read<Int>(response.body, "$.neo4jHealthchecks.nodeCount").should.equal(0)
        }
    }

    @Test
    fun `Checking the healthchecks with nodes in the database`() {
        driver.executeStatement("CREATE (n:ArbitraryNode {id: {id}})",
                mapOf("id" to UUID.randomUUID().toString()),
                AccessMode.WRITE)

        requester.get("/health").let { response ->
            response.statusCode.should.equal(HttpStatus.OK)
            JsonPath.read<String>(response.body, "$.status").should.equal("UP")
            JsonPath.read<Int>(response.body, "$.neo4jHealthchecks.nodeCount").should.equal(1)
        }
    }
}