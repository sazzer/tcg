package uk.co.grahamcox.tcg.webapp.cucumber.requests

import com.winterbe.expekt.should
import cucumber.api.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.ResponseMatcher

/**
 * Steps for checking the status of the last received response
 */
class ResponseStatusSteps {
    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The local server port */
    @LocalServerPort
    private lateinit var serverPort: Integer

    /** The matcher to use for error responses */
    @Autowired
    @Qualifier("errorResponseMatcher")
    private lateinit var errorResponseMatcher: ResponseMatcher

    @Then("""^I get an? "(.+)" response$""")
    fun checkResponseStatus(statusName: String) {
        val response = requester.lastResponse
        val status = HttpStatus.valueOf(statusName.toUpperCase().replace(" ", "_"))

        response.statusCode.should.equal(status)
    }

    @Then("""^I get a redirect to URL "(.+)" with parameters:$""")
    fun checkRedirectUrl(url: String, params: Map<String, String>) {
        val response = requester.lastResponse
        val location = response.headers.location
        location.should.not.be.`null`

        UriComponentsBuilder.fromUri(location)
                .port(null) // We can't predict the port
                .replaceQuery(null)
                .fragment(null)
                .build()
                .toUriString()
                .should.equal(url)

        val queryParams = UriComponentsBuilder.fromUri(location)
                .build()
                .queryParams

        queryParams.should.contain.keys(*params.keys.toTypedArray())
        params.forEach { key, expected ->
            val value = queryParams[key]!!
            value.size.should.equal(1)
            value[0].should.equal(expected.replace("\${serverPort}", serverPort.toString()))
        }
    }

    @Then("""^I get an error response of:$""")
    fun checkErrorResponse(expected: Map<String, String>) {
        errorResponseMatcher.match(expected)
    }
}