package uk.co.grahamcox.tcg.webapp.cucumber.users

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import uk.co.grahamcox.tcg.webapp.cucumber.Requester

/**
 * Cucumber steps for working with user records
 */
class UserSteps {
    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The user database matcher to use */
    @Autowired
    private lateinit var userDatabaseMatcher: UserDatabaseMatcher

    /** The user response matcher to use */
    @Autowired
    private lateinit var userResponseMatcher: UserResponseMatcher

    /** The user seeder to use */
    @Autowired
    private lateinit var userSeeder: UserSeeder

    @Given("""^I have a User with details:$""")
    fun seedUser(userDetails: Map<String, String>) {
        userSeeder.seedUser(userDetails)
    }

    @When("""^I retrieve the currently logged in user$""")
    fun retrieveCurrentUser() {
        requester.get("/api/users/me")
    }
    
    @Then("""^the database contains a user for this User ID with details:$""")
    fun checkUserMatches(userDetails: Map<String, String>) {
        val response = requester.lastResponseBodyAsJson
        val userId = response["userId"]!!.toString()
        userDatabaseMatcher.matchUser(userId, userDetails)
    }

    @Then("""^I received user details:$""")
    fun checkUserResponseMatches(expected: Map<String, String>) {
        userResponseMatcher.matchUser(expected)
    }
}