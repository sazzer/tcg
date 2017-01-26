package uk.co.grahamcox.tcg.webapp.cucumber.stats

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import uk.co.grahamcox.tcg.webapp.cucumber.Requester
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.ResponseMatcher

class StatsSteps {

    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The stat response matcher to use */
    @Autowired
    @Qualifier("statResponseMatcher")
    private lateinit var statResponseMatcher: ResponseMatcher

    /** The means to seed Stats records */
    @Autowired
    private lateinit var statSeeder: StatSeeder

    @Given("""^I have a Statistic with details:$""")
    fun seedStat(statDetails: Map<String, String>) {
        statSeeder.seed(statDetails)
    }

    @When("""^I retrieve the stat "(.+)"""")
    fun retrieveStat(id: String) {
        requester.get("/api/stats/${id}")
    }

    @Then("""^I received statistic:$""")
    fun checkStatResponseMatches(expected: Map<String, String>) {
        statResponseMatcher.match(expected)
    }
}