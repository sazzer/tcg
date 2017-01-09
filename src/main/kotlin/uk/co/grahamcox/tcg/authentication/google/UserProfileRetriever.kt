package uk.co.grahamcox.tcg.authentication.google

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.web.client.RestTemplate

/**
 * Mechanism to retrieve the User Profile for a Google Access Token
 * @property config The Google configuration
 * @property restTemplate The REST Template to use to talk to Google
 */
class UserProfileRetriever(
        private val config: GoogleConfig,
        private val restTemplate: RestTemplate
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(UserProfileRetriever::class.java)
    }

    /**
     * Retrieve the User Profile for the user that has just logged in
     * @param accessToken the access token the user has been assigned
     * @return the user profile
     */
    fun retrieveUserProfile(accessToken: String): UserProfile {
        LOG.debug("Retrieving user profile for user with access token {}", accessToken)
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer ${accessToken}")

        val userProfileResponse = restTemplate.exchange(
                RequestEntity(
                        null,
                        headers,
                        HttpMethod.GET,
                        config.userProfileUrl
                ),
                UserProfile::class.java
        )
        LOG.info("Retrieved user profile details: {}", userProfileResponse)
        return userProfileResponse.body
    }
}