package uk.co.grahamcox.tcg.webapp.cucumber.authentication

import cucumber.api.java.en.Given
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import uk.co.grahamcox.tcg.authentication.token.AccessToken
import uk.co.grahamcox.tcg.authentication.token.AccessTokenEncoder
import uk.co.grahamcox.tcg.authentication.token.TokenId
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.webapp.cucumber.Requester
import java.util.*

/**
 * Steps for working directly with Access Tokens
 */
class AccessTokenSteps {
    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The means to generate an encoded access token */
    @Autowired
    private lateinit var accessTokenEncoder: AccessTokenEncoder

    @Given("""^I have obtained an Access Token for user "(.+)"$""")
    fun obtainAccessToken(userId: String) {
        val accessToken = AccessToken(
                userId = UserId(userId),
                tokenId = TokenId(UUID.randomUUID().toString())
        )
        val encodedAccessToken = accessTokenEncoder.encodeAccessToken(accessToken)
        requester.accessToken = encodedAccessToken.accessToken
    }

    @When("""^I check my access token details$""")
    fun checkAccessToken() {
        requester.get("/api/authentication/accessToken")
    }
}