package uk.co.grahamcox.tcg.authentication.google

import com.winterbe.expekt.should
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate

/**
 * Unit tests for [UserProfileRetriever]
 */
class UserProfileRetrieverTest {
    /** The REST Template to use */
    private val restTemplate = RestTemplate()
    /** The mock server to use */
    private val mockServer = MockRestServiceServer.createServer(restTemplate)
    /** The test subject */
    private val testSubject = UserProfileRetriever(
            config = TestData.googleConfig,
            restTemplate = restTemplate
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
    fun `successfully retrieve User Profile`() {
        val response = """{
 "emails": [
  {
   "value": "graham@grahamcox.co.uk",
   "type": "account"
  }
 ],
 "id": "0987654321",
 "displayName": "Graham Cox",
 "verified": false
}"""
        mockServer.expect(MockRestRequestMatchers.requestTo(TestData.googleConfig.userProfileUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andExpect(MockRestRequestMatchers.header("Authorization", "Bearer abc123"))
                .andRespond(MockRestResponseCreators.withSuccess(response, MediaType.APPLICATION_JSON))

        val userProfile = testSubject.retrieveUserProfile("abc123")
        userProfile.id.should.equal("0987654321")
        userProfile.displayName.should.equal("Graham Cox")
        userProfile.verified.should.be.`false`
        userProfile.emails.should.have.size(1)
        userProfile.emails[0].type.should.equal("account")
        userProfile.emails[0].value.should.equal("graham@grahamcox.co.uk")
    }

    @Test(expected = UserProfileRetrievalException::class)
    fun `unsuccessfully retrieve User Profile`() {
        val response = """{"error": "invalid_grant", "error_description": "Code was already redeemed."}"""
        mockServer.expect(MockRestRequestMatchers.requestTo(TestData.googleConfig.userProfileUrl))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andExpect(MockRestRequestMatchers.header("Authorization", "Bearer abc123"))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.FORBIDDEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response))
        testSubject.retrieveUserProfile("abc123")
    }
}