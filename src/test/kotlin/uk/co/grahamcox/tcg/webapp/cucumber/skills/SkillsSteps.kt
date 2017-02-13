package uk.co.grahamcox.tcg.webapp.cucumber.skills

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
 * Cucumber steps for working with Skills
 */
class SkillsSteps {

    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The skill response matcher to use */
    @Autowired
    @Qualifier("skillResponseMatcher")
    private lateinit var skillResponseMatcher: ResponseMatcher

    /** The means to seed Skill records */
    @Autowired
    @Qualifier("skillSeeder")
    private lateinit var skillSeeder: MongoSeeder

    /** URL Builder for building URLs to list Skills */
    @Autowired
    @Qualifier("listSkillsUrlBuilder")
    private lateinit var listSkillsUrlBuilder: UrlBuilder

    @Given("""^I have an skill with details:$""")
    fun seedSkill(skillDetails: Map<String, String>) {
        skillSeeder.seed(skillDetails)
    }

    @When("""^I retrieve the skill "(.+)"""")
    fun retrieveSkill(id: String) {
        requester.get("/api/skills/${id}")
    }

    @When("""^I retrieve the list of skills$""")
    fun retrieveSkillList() {
        retrieveSkillList(mapOf())
    }

    @When("""^I retrieve the list of skills:$""")
    fun retrieveSkillList(options: Map<String, String>) {
        requester.get(listSkillsUrlBuilder.build(options))
    }

    @Then("""^I received skill:$""")
    fun checkSkillResponseMatches(expected: Map<String, String>) {
        skillResponseMatcher.match(expected)
    }

    @Then("""^skill (\d+) has details:$""")
    fun checkSkillResponseMatchesInList(offset: Int, expected: Map<String, String>){
        skillResponseMatcher.match(offset, expected)
    }
}