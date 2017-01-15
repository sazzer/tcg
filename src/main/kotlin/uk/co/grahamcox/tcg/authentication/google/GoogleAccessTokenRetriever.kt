package uk.co.grahamcox.tcg.authentication.google

import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import uk.co.grahamcox.tcg.authentication.RedirectGenerator

/**
 * Mechanism to retrieve an Access Token from Google for an Authorization Code
 * @property config The Google configuration
 * @property redirectGenerator The redirect generator to use
 * @property restTemplate The REST Template to use to talk to Google
 */
class GoogleAccessTokenRetriever(
        private val config: GoogleConfig,
        private val redirectGenerator: RedirectGenerator,
        private val restTemplate: RestTemplate
) {
    companion object {
        private val LOG = LoggerFactory.getLogger(GoogleAccessTokenRetriever::class.java)
    }
    /**
     * Actually retrieve the access token
     * @param authCode The code to swap for the access token
     * @return the access token
     */
    fun retrieveAccessToken(authCode: String): AccessTokenResponse {
        LOG.debug("Getting access token for authorization code {}", authCode)
        val params = mapOf(
                "code" to authCode,
                "client_id" to config.clientId,
                "client_secret" to config.clientSecret,
                "redirect_uri" to redirectGenerator.generate().toString(),
                "grant_type" to "authorization_code"
        )
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        try {
            val tokenResponse = restTemplate.exchange(
                    RequestEntity(
                            LinkedMultiValueMap(params.mapValues { listOf(it.value) }),
                            headers,
                            HttpMethod.POST,
                            config.tokenUrl
                    ),
                    AccessTokenResponse::class.java
            )

            LOG.debug("Received token response: {}", tokenResponse)
            return tokenResponse.body
        } catch (e: HttpClientErrorException) {
            LOG.warn("Failed to retrieve Access Token", e)
            throw AccessTokenRetrievalException()
        }
    }
}