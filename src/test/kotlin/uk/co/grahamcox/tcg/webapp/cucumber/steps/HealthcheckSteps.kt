package uk.co.grahamcox.tcg.webapp.cucumber.steps

import com.jayway.jsonpath.JsonPath
import com.winterbe.expekt.should
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

/**
 * Steps for healthchecks of the system
 */
class HealthcheckSteps {
    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    @When("^I check the health of the system$")
    fun checkHealth() {
        requester.get("/health")
    }

    @Then("^the healthchecks indicate that the system is Up$")
    fun checkHealthResponseOk() {
        val lastResponse = requester.lastResponse
        lastResponse.statusCode.should.equal(HttpStatus.OK)
        val systemStatus = JsonPath.read<String>(lastResponse.body, "$.status")
        systemStatus.should.equal("UP")
    }
}