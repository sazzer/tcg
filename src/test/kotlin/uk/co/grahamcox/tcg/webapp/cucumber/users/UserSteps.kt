package uk.co.grahamcox.tcg.webapp.cucumber.users

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import uk.co.grahamcox.tcg.webapp.cucumber.Requester
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.DatabaseMatcher
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.ResponseMatcher
import uk.co.grahamcox.tcg.webapp.cucumber.seeder.MongoSeeder

/**
 * Cucumber steps for working with user records
 */
class UserSteps {
    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The user database matcher to use */
    @Autowired
    @Qualifier("userDatabaseMatcher")
    private lateinit var userDatabaseMatcher: DatabaseMatcher

    /** The user response matcher to use */
    @Autowired
    @Qualifier("userResponseMatcher")
    private lateinit var userResponseMatcher: ResponseMatcher

    /** The user seeder to use */
    @Autowired
    @Qualifier("userSeeder")
    private lateinit var userSeeder: MongoSeeder

    @Given("""^I have a User with details:$""")
    fun seedUser(userDetails: Map<String, String>) {
        userSeeder.seed(userDetails)
    }

    @When("""^I retrieve the currently logged in user$""")
    fun retrieveCurrentUser() {
        requester.get("/api/users/me")
    }
    
    @Then("""^the database contains a user for this User ID with details:$""")
    fun checkUserMatches(userDetails: Map<String, String>) {
        val response = requester.lastResponseBodyAsJson
        val userId = response["userId"]!!.toString()
        userDatabaseMatcher.match(userId, userDetails)
    }

    @Then("""^I received user details:$""")
    fun checkUserResponseMatches(expected: Map<String, String>) {
        userResponseMatcher.match(expected)
    }
}