package uk.co.grahamcox.tcg.webapp.cucumber.attributes

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import uk.co.grahamcox.tcg.webapp.cucumber.Requester
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.ResponseMatcher

/**
 * Cucumber steps for working with Attributes
 */
class AttributesSteps {

    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The attribute response matcher to use */
    @Autowired
    @Qualifier("attributeResponseMatcher")
    private lateinit var attributeResponseMatcher: ResponseMatcher

    /** The means to seed Attribute records */
    @Autowired
    private lateinit var attributeSeeder: AttributeSeeder

    @Given("""^I have an attribute with details:$""")
    fun seedAttribute(attributeDetails: Map<String, String>) {
        attributeSeeder.seed(attributeDetails)
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