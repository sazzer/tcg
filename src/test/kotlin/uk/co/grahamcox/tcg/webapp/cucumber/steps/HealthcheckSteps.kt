package uk.co.grahamcox.tcg.webapp.cucumber.steps

import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired

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
}