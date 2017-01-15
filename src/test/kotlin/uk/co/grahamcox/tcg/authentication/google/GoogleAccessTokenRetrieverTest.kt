package uk.co.grahamcox.tcg.authentication.google

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URI

/**
 * Unit tests for [GoogleAccessTokenRetriever]
 */
class GoogleAccessTokenRetrieverTest {
    /** The REST Template to use */
    private val restTemplate = RestTemplate()
    /** The mock server to use */
    private val mockServer = MockRestServiceServer.createServer(restTemplate)
    /** The test subject */
    private val testSubject = GoogleAccessTokenRetriever(
            config = TestData.googleConfig,
            redirectGenerator = mock {
                on { this.generate() } doReturn URI("http://redirect.example.com")
            },
            restTemplate = restTemplate
    )

    /** The authorization code to use */
    private val authCode = "abc123"

    /** The expected request data */
    private val expectedRequest = LinkedMultiValueMap(
            mapOf(
                    "code" to listOf(authCode),
                    "client_id" to listOf(TestData.googleConfig.clientId),
                    "client_secret" to listOf(TestData.googleConfig.clientSecret),
                    "redirect_uri" to listOf("http://redirect.example.com"),
                    "grant_type" to listOf("authorization_code")
            )
    )

    /**
     * Reset the server
     */
    @Before
    fun setup() {
        mockServer.reset()
    }

    /**
     * Verify the server was called as expected
     */
    @After
    fun verify() {
        mockServer.verify()
    }

    @Test
    fun `successfully retrieve Access Token`() {
        val response = """{"access_token": "thisIsAnAccessToken","token_type": "Bearer","expires_in": 3600}"""
        mockServer.expect(MockRestRequestMatchers.requestTo(TestData.googleConfig.tokenUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockRestRequestMatchers.content().formData(expectedRequest))
                .andRespond(MockRestResponseCreators.withSuccess(response, MediaType.APPLICATION_JSON))

        val accessToken = testSubject.retrieveAccessToken(authCode)
        accessToken.accessToken.should.equal("thisIsAnAccessToken")
        accessToken.expiresIn.should.equal(3600)
        accessToken.tokenType.should.equal("Bearer")
        accessToken.idToken.should.be.`null`
    }

    @Test
    fun `successfully retrieve Access Token and ID Token`() {
        val response = """{"access_token": "thisIsAnAccessToken","token_type": "Bearer","expires_in": 3600, "id_token":"thisIsAnIdToken"}"""
        mockServer.expect(MockRestRequestMatchers.requestTo(TestData.googleConfig.tokenUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockRestRequestMatchers.content().formData(expectedRequest))
                .andRespond(MockRestResponseCreators.withSuccess(response, MediaType.APPLICATION_JSON))

        val accessToken = testSubject.retrieveAccessToken(authCode)
        accessToken.accessToken.should.equal("thisIsAnAccessToken")
        accessToken.expiresIn.should.equal(3600)
        accessToken.tokenType.should.equal("Bearer")
        accessToken.idToken.should.equal("thisIsAnIdToken")
    }

    @Test(expected = AccessTokenRetrievalException::class)
    fun `unsuccessfully retrieve Access Token`() {
        val response = """{"error": "invalid_grant", "error_description": "Code was already redeemed."}"""
        mockServer.expect(MockRestRequestMatchers.requestTo(TestData.googleConfig.tokenUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockRestRequestMatchers.content().formData(expectedRequest))
                .andRespond(MockRestResponseCreators.withBadRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response))

        testSubject.retrieveAccessToken(authCode)
    }
}