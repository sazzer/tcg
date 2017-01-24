package uk.co.grahamcox.tcg.webapp.cucumber.authentication

import com.winterbe.expekt.should
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.util.LinkedMultiValueMap
import uk.co.grahamcox.tcg.webapp.cucumber.Requester

/**
 * Cucumber steps for authentication workflows
 */
class AuthenticationSteps {
    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The local server port */
    @LocalServerPort
    private lateinit var serverPort: Integer

    /** The Mock Server to use */
    @Autowired
    private lateinit var mockServer: MockRestServiceServer


    @Given("""^I expect a request to Google to exchange Authorization Code "(.+)" for Access Token "(.+)"$""")
    fun expectAuthCodeRequest(authCode: String, accessToken: String) {
        val expectedAccessTokenRequest = LinkedMultiValueMap(
                mapOf(
                        "code" to listOf(authCode),
                        "client_id" to listOf("myGoogleClientId"),
                        "client_secret" to listOf("myGoogleClientSecret"),
                        "redirect_uri" to listOf("http://localhost:${serverPort}/api/authentication/google/redirect"),
                        "grant_type" to listOf("authorization_code")
                )
        )

        val accessTokenResponse = """{"access_token": "$accessToken","token_type": "Bearer","expires_in": 3600}"""
        mockServer.expect(MockRestRequestMatchers.requestTo("https://www.googleapis.com/oauth2/v4/token"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockRestRequestMatchers.content().formData(expectedAccessTokenRequest))
                .andRespond(MockRestResponseCreators.withSuccess(accessTokenResponse, MediaType.APPLICATION_JSON))
    }

    @Given("""^I expect a request to Google for the current user of Access Token "(.+)":$""")
    fun expectUserProfileRequest(accessToken: String, userProfile: Map<String, String>) {
        val response = """{
 "emails": [
  {
   "value": "${userProfile["Email"]}",
   "type": "account"
  }
 ],
 "id": "${userProfile["User ID"]}",
 "displayName": "${userProfile["Name"]}",
 "verified": false
}"""
        mockServer.expect(MockRestRequestMatchers.requestTo("https://www.googleapis.com/plus/v1/people/me"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andExpect(MockRestRequestMatchers.header("Authorization", "Bearer $accessToken"))
                .andRespond(MockRestResponseCreators.withSuccess(response, MediaType.APPLICATION_JSON))
    }

    @When("""^I start authentication with provider "(.+)"$""")
    fun startAuthentication(provider: String) {
        requester.get("/api/authentication/${provider}/start")
    }

    @When("""^I receive an authentication callback from provider "(.+)" with parameters:$""")
    fun authenticationCallback(provider: String, parameters: Map<String, String>) {
        requester.get("/api/authentication/${provider}/redirect", parameters)
    }

    @Then("""^I get an Access Token response$""")
    fun checkResponseIsAccessToken() {
        val response = requester.lastResponseBodyAsJson
        response.should.contain.keys("userId", "accessToken", "expires")
    }
}