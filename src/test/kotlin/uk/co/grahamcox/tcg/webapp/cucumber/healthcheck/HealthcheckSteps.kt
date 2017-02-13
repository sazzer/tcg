package uk.co.grahamcox.tcg.webapp.cucumber.healthcheck

import com.winterbe.expekt.should
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import uk.co.grahamcox.tcg.webapp.cucumber.requests.Requester

/**
 * Cucumber steps for the healthchecks
 */
class HealthcheckSteps {
    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    @When("""^I check the health of the system$""")
    fun checkHealth() {
        requester.get("/health")
    }

    @Then("""^the system is healthy$""")
    fun checkSystemHealthy() {
        val response = requester.lastResponse
        response.statusCode.should.equal(HttpStatus.OK)
        response.body.should.contain("status" to "UP")
    }
}