package uk.co.grahamcox.tcg.webapp.cucumber.abilities

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
 * Cucumber steps for working with Abilities
 */
class AbilitiesSteps {

    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The ability response matcher to use */
    @Autowired
    @Qualifier("abilityResponseMatcher")
    private lateinit var abilityResponseMatcher: ResponseMatcher

    /** The means to seed Ability records */
    @Autowired
    @Qualifier("abilitieseeder")
    private lateinit var abilitieseeder: MongoSeeder

    /** URL Builder for building URLs to list Abilities */
    @Autowired
    @Qualifier("listAbilitiesUrlBuilder")
    private lateinit var listAbilitiesUrlBuilder: UrlBuilder

    @Given("""^I have an ability with details:$""")
    fun seedAbility(abilityDetails: Map<String, String>) {
        abilitieseeder.seed(abilityDetails)
    }

    @When("""^I retrieve the ability "(.+)"""")
    fun retrieveAbility(id: String) {
        requester.get("/api/abilities/${id}")
    }

    @When("""^I retrieve the list of abilities$""")
    fun retrieveAbilityList() {
        retrieveAbilityList(mapOf())
    }

    @When("""^I retrieve the list of abilities:$""")
    fun retrieveAbilityList(options: Map<String, String>) {
        requester.get(listAbilitiesUrlBuilder.build(options))
    }

    @Then("""^I received ability:$""")
    fun checkAbilityResponseMatches(expected: Map<String, String>) {
        abilityResponseMatcher.match(expected)
    }

    @Then("""^ability (\d+) has details:$""")
    fun checkAbilityResponseMatchesInList(offset: Int, expected: Map<String, String>){
        abilityResponseMatcher.match(offset, expected)
    }
}