package uk.co.grahamcox.tcg.webapp.cucumber.attributes

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import uk.co.grahamcox.tcg.webapp.cucumber.Requester
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.ResponseMatcher

class AttributesSteps {

    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The stat response matcher to use */
    @Autowired
    @Qualifier("attributeResponseMatcher")
    private lateinit var attributeResponseMatcher: ResponseMatcher

    /** The means to seed Stats records */
    @Autowired
    private lateinit var attributeSeeder: AttributeSeeder

    @Given("""^I have an attribute with details:$""")
    fun seedAttribute(statDetails: Map<String, String>) {
        attributeSeeder.seed(statDetails)
    }

    @When("""^I retrieve the attribute "(.+)"""")
    fun retrieveAttribute(id: String) {
        requester.get("/api/attributes/${id}")
    }

    @Then("""^I received attribute:$""")
    fun checkAttributeResponseMatches(expected: Map<String, String>) {
        attributeResponseMatcher.match(expected)
    }
}