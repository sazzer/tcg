package uk.co.grahamcox.tcg.webapp.acceptance.authentication

import com.jayway.jsonpath.JsonPath
import com.winterbe.expekt.should
import org.junit.Test
import org.neo4j.driver.v1.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.tcg.authentication.google.TestData
import uk.co.grahamcox.tcg.neo4j.executeStatement
import uk.co.grahamcox.tcg.webapp.acceptance.AcceptanceTestBase
import uk.co.grahamcox.tcg.webapp.acceptance.Requester

/**
 * Acceptance Tests for authentication with Google
 */
class GoogleAuthenticationProviderIT : AcceptanceTestBase() {
    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    /** The Mock Server to use */
    @Autowired
    private lateinit var mockServer: MockRestServiceServer

    /** The port the server is listening on */
    @LocalServerPort
    private lateinit var serverPort: Integer

    /** The Neo4J connection to use */
    @Autowired
    private lateinit var neo4j: Driver

    /** The User Seeder */
    @Autowired
    private lateinit var userSeeder: UserSeeder

    @Test
    fun `start authentication with Google`() {
        requester.get("/api/authentication/google/start").let { response ->
            response.statusCode.should.equal(HttpStatus.FOUND)
            val location = UriComponentsBuilder.fromUri(response.headers.location).build()
            location.scheme.should.equal("https")
            location.host.should.equal("accounts.google.com")
            location.path.should.equal("/o/oauth2/v2/auth")
            location.queryParams.size.should.equal(5)
            location.queryParams["client_id"].should.equal(listOf("myGoogleClientId"))
            location.queryParams["response_type"].should.equal(listOf("code"))
            location.queryParams["scope"].should.equal(listOf("openid%20email"))
            location.queryParams["redirect_uri"].should.equal(listOf("http://localhost:${serverPort}/api/authentication/google/redirect"))
            location.queryParams["state"].should.have.size(1)
            location.queryParams["state"]?.get(0).should.not.be.empty
        }
    }

    @Test
    fun `Handle callback after authentication of new user with Google`() {
        expectAccessTokenRequest("abc123", "thisIsMyAccessToken")
        expectUserProfileRequest("thisIsMyAccessToken", "1234567890")

        requester.get("/api/authentication/google/redirect", mapOf(
                "state" to "theState",
                "code" to "abc123"
        )).let { response ->
            response.statusCode.should.equal(HttpStatus.OK)
            JsonPath.read<String>(response.body, "$.accessToken").should.not.be.empty
            val userId = JsonPath.read<String>(response.body, "$.userId")
            userId.should.not.be.empty
            JsonPath.read<String>(response.body, "$.expires").should.not.be.empty

            neo4j.executeStatement("MATCH (u:User {id:{id}}) RETURN u", mapOf("id" to userId)).let { statementResponse ->
                val userRecord = statementResponse.single().get("u").asNode()
                userRecord.get("name").asString().should.equal("Graham Cox")
                userRecord.get("email").asString().should.equal("graham@grahamcox.co.uk")
            }
        }
    }

    @Test
    fun `Handle callback after authentication of known user with Google`() {
        userSeeder.createUser("Test User", "test@example.com", mapOf("google" to "1234567890"))
        expectAccessTokenRequest("abc123", "thisIsMyAccessToken")
        expectUserProfileRequest("thisIsMyAccessToken", "1234567890")

        requester.get("/api/authentication/google/redirect", mapOf(
                "state" to "theState",
                "code" to "abc123"
        )).let { response ->
            response.statusCode.should.equal(HttpStatus.OK)
            JsonPath.read<String>(response.body, "$.accessToken").should.not.be.empty
            val userId = JsonPath.read<String>(response.body, "$.userId")
            userId.should.not.be.empty
            JsonPath.read<String>(response.body, "$.expires").should.not.be.empty

            neo4j.executeStatement("MATCH (u:User {id:{id}}) RETURN u", mapOf("id" to userId)).let { statementResponse ->
                val userRecord = statementResponse.single().get("u").asNode()
                userRecord.get("name").asString().should.equal("Test User")
                userRecord.get("email").asString().should.equal("test@example.com")
            }
        }
    }

    /**
     * Helper to expect a call to Google to exchange an Auth Code for an Access Token
     * @param authCode the auth code to exchange
     * @param accessToken the access token to exchange it for
     */
    private fun expectAccessTokenRequest(authCode: String, accessToken: String) {
        val expectedAccessTokenRequest = LinkedMultiValueMap(
                mapOf(
                        "code" to listOf(authCode),
                        "client_id" to listOf("myGoogleClientId"),
                        "client_secret" to listOf("myGoogleClientSecret"),
                        "redirect_uri" to listOf("http://localhost:${serverPort}/api/authentication/google/redirect"),
                        "grant_type" to listOf("authorization_code")
                )
        )

        val accessTokenResponse = """{"access_token": "${accessToken}","token_type": "Bearer","expires_in": 3600}"""
        mockServer.expect(MockRestRequestMatchers.requestTo("https://www.googleapis.com/oauth2/v4/token"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockRestRequestMatchers.content().formData(expectedAccessTokenRequest))
                .andRespond(MockRestResponseCreators.withSuccess(accessTokenResponse, MediaType.APPLICATION_JSON))
    }

    /**
     * Expect a request for a user profile for the given access token
     * @param accessToken The access token
     */
    private fun expectUserProfileRequest(accessToken: String, userId: String) {
        val response = """{
 "emails": [
  {
   "value": "graham@grahamcox.co.uk",
   "type": "account"
  }
 ],
 "id": "${userId}",
 "displayName": "Graham Cox",
 "verified": false
}"""
        mockServer.expect(MockRestRequestMatchers.requestTo("https://www.googleapis.com/plus/v1/people/me"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andExpect(MockRestRequestMatchers.header("Authorization", "Bearer ${accessToken}"))
                .andRespond(MockRestResponseCreators.withSuccess(response, MediaType.APPLICATION_JSON))
    }
}