package uk.co.grahamcox.tcg.webapp.cucumber.races

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import uk.co.grahamcox.tcg.webapp.cucumber.requests.Requester
import uk.co.grahamcox.tcg.webapp.cucumber.requests.UrlBuilder
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.ResponseMatcher
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder

/**
 * Cucumber steps for working with Races
 */
class RacesSteps {

    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The race response matcher to use */
    @Autowired
    @Qualifier("raceResponseMatcher")
    private lateinit var raceResponseMatcher: ResponseMatcher

    /** The means to seed Race records */
    @Autowired
    @Qualifier("raceSeeder")
    private lateinit var raceSeeder: MongoSeeder

    /** URL Builder for building URLs to list Races */
    @Autowired
    @Qualifier("listRacesUrlBuilder")
    private lateinit var listRacesUrlBuilder: UrlBuilder

    @Given("""^I have an race with details:$""")
    fun seedRace(raceDetails: Map<String, String>) {
        raceSeeder.seed(raceDetails)
    }

    @When("""^I retrieve the race "(.+)"""")
    fun retrieveRace(id: String) {
        requester.get("/api/races/${id}")
    }

    @When("""^I retrieve the list of races$""")
    fun retrieveRaceList() {
        retrieveRaceList(mapOf())
    }

    @When("""^I retrieve the list of races:$""")
    fun retrieveRaceList(options: Map<String, String>) {
        requester.get(listRacesUrlBuilder.build(options))
    }

    @Then("""^I received race:$""")
    fun checkRaceResponseMatches(expected: Map<String, String>) {
        raceResponseMatcher.match(expected)
    }

    @Then("""^race (\d+) has details:$""")
    fun checkRaceResponseMatchesInList(offset: Int, expected: Map<String, String>){
        raceResponseMatcher.match(offset, expected)
    }
}