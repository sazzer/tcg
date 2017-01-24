package uk.co.grahamcox.tcg.webapp.cucumber.users

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import uk.co.grahamcox.tcg.webapp.cucumber.Requester

/**
 * Cucumber steps for working with user records
 */
class UserSteps {
    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The user matcher to use */
    @Autowired
    private lateinit var userMatcher: UserMatcher

    /** The user seeder to use */
    @Autowired
    private lateinit var userSeeder: UserSeeder

    @Given("""^I have a User with details:$""")
    fun seedUser(userDetails: Map<String, String>) {
        userSeeder.seedUser(userDetails)
    }
    
    @Then("""^the database contains a user for this User ID with details:$""")
    fun checkUserMatches(userDetails: Map<String, String>) {
        val response = requester.lastResponseBodyAsJson
        val userId = response["userId"]!!.toString()
        userMatcher.matchUser(userId, userDetails)
    }
}