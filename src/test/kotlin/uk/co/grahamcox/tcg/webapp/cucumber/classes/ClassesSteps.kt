package uk.co.grahamcox.tcg.webapp.cucumber.classes

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
 * Cucumber steps for working with Classes
 */
class ClassesSteps {

    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The class response matcher to use */
    @Autowired
    @Qualifier("classResponseMatcher")
    private lateinit var classResponseMatcher: ResponseMatcher

    /** The means to seed Class records */
    @Autowired
    @Qualifier("classeseeder")
    private lateinit var classSeeder: MongoSeeder

    /** URL Builder for building URLs to list Classes */
    @Autowired
    @Qualifier("listClassesUrlBuilder")
    private lateinit var listClassesUrlBuilder: UrlBuilder

    @Given("""^I have an class with details:$""")
    fun seedClass(classDetails: Map<String, String>) {
        classSeeder.seed(classDetails)
    }

    @When("""^I retrieve the class "(.+)"""")
    fun retrieveClass(id: String) {
        requester.get("/api/classes/${id}")
    }

    @When("""^I retrieve the list of classes$""")
    fun retrieveClassList() {
        retrieveClassList(mapOf())
    }

    @When("""^I retrieve the list of classes:$""")
    fun retrieveClassList(options: Map<String, String>) {
        requester.get(listClassesUrlBuilder.build(options))
    }

    @Then("""^I received class:$""")
    fun checkClassResponseMatches(expected: Map<String, String>) {
        classResponseMatcher.match(expected)
    }

    @Then("""^class (\d+) has details:$""")
    fun checkClassResponseMatchesInList(offset: Int, expected: Map<String, String>){
        classResponseMatcher.match(offset, expected)
    }
}