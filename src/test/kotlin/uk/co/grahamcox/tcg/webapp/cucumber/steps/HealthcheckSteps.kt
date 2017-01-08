package uk.co.grahamcox.tcg.webapp.cucumber.steps

import com.jayway.jsonpath.JsonPath
import com.winterbe.expekt.should
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.neo4j.driver.v1.AccessMode
import org.neo4j.driver.v1.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import uk.co.grahamcox.tcg.neo4j.executeStatement
import java.util.*

/**
 * Steps for healthchecks of the system
 */
class HealthcheckSteps {
    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    /** The database driver. Note - don't do this */
    @Autowired
    private lateinit var driver: Driver

    @Given("^I have an arbitrary node in the database$")
    fun createArbitraryNode() {
        driver.executeStatement("CREATE (n:ArbitraryNode {id: {id}})",
                mapOf("id" to UUID.randomUUID().toString()),
                AccessMode.WRITE)
    }

    @When("^I check the health of the system$")
    fun checkHealth() {
        requester.get("/health")
    }

    @Then("^the healthchecks indicate that the system is Up$")
    fun checkHealthResponseOk() {
        val lastResponse = requester.lastResponse
        lastResponse.statusCode.should.equal(HttpStatus.OK)
        JsonPath.read<String>(lastResponse.body, "$.status").should.equal("UP")
    }

    @Then("^there (?:is|are) (\\d+) Neo4j Nodes? in the database$")
    fun checkHealthcheckNodeCount(nodeCount: Int) {
        val lastResponse = requester.lastResponse
        JsonPath.read<Int>(lastResponse.body, "$.neo4jHealthchecks.nodeCount").should.equal(nodeCount)
    }
}