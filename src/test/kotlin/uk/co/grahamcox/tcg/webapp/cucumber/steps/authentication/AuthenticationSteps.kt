package uk.co.grahamcox.tcg.webapp.cucumber.steps.authentication

import com.winterbe.expekt.should
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.tcg.webapp.cucumber.steps.Requester

/**
 * Cucumber steps for authentication
 */
class AuthenticationSteps {
    /** The server port */
    @LocalServerPort
    private lateinit var serverPort: Integer

    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    @When("^I initiate authentication against \"(.+)\"$")
    fun startAuthentication(provider: String) {
        requester.get("/api/authentication/${provider}/start")
    }

    @Then("^I get a redirect to \"(.+)\" with parameters:")
    fun checkAuthenticationRedirect(urlBase: String, parameters: Map<String, String>) {
        val lastResponse = requester.lastResponse
        lastResponse.statusCode.should.equal(HttpStatus.FOUND)
        val location = lastResponse.headers.location
        location.should.not.be.`null`

        UriComponentsBuilder.fromUri(location).replaceQuery("").build().toUri().toString().should.equal(urlBase)
        val queryParams = UriComponentsBuilder.fromUri(location).build().queryParams
        parameters.forEach { param, value ->
            queryParams[param].should.be.size(1)
            val realValue = if (param == "redirect_uri") {
                UriComponentsBuilder.fromUriString(value)
                        .port(serverPort.toInt())
                        .build()
                        .toUriString()
            } else {
                value
            }
            queryParams[param].should.equal(listOf(realValue))
        }
    }
}