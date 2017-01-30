package uk.co.grahamcox.tcg.webapp.cucumber.genders

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import uk.co.grahamcox.tcg.webapp.cucumber.Requester
import uk.co.grahamcox.tcg.webapp.cucumber.UrlBuilder
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.ResponseMatcher
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder

/**
 * Cucumber steps for working with Genders
 */
class GendersSteps {

    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The gender response matcher to use */
    @Autowired
    @Qualifier("genderResponseMatcher")
    private lateinit var genderResponseMatcher: ResponseMatcher

    /** The means to seed Gender records */
    @Autowired
    @Qualifier("genderSeeder")
    private lateinit var genderSeeder: MongoSeeder

    /** URL Builder for building URLs to list Genders */
    @Autowired
    @Qualifier("listGendersUrlBuilder")
    private lateinit var listGendersUrlBuilder: UrlBuilder

    @Given("""^I have an gender with details:$""")
    fun seedGender(genderDetails: Map<String, String>) {
        genderSeeder.seed(genderDetails)
    }

    @When("""^I retrieve the gender "(.+)"""")
    fun retrieveGender(id: String) {
        requester.get("/api/genders/${id}")
    }

    @When("""^I retrieve the list of genders$""")
    fun retrieveGenderList() {
        retrieveGenderList(mapOf())
    }

    @When("""^I retrieve the list of genders:$""")
    fun retrieveGenderList(options: Map<String, String>) {
        requester.get(listGendersUrlBuilder.build(options))
    }

    @Then("""^I received gender:$""")
    fun checkGenderResponseMatches(expected: Map<String, String>) {
        genderResponseMatcher.match(expected)
    }

    @Then("""^gender (\d+) has details:$""")
    fun checkGenderResponseMatchesInList(offset: Int, expected: Map<String, String>){
        genderResponseMatcher.match(offset, expected)
    }
}