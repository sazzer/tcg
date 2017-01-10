package uk.co.grahamcox.tcg.webapp.spring

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestTemplate
import uk.co.grahamcox.tcg.authentication.AuthenticationProvider
import uk.co.grahamcox.tcg.authentication.NonceGenerator
import uk.co.grahamcox.tcg.authentication.ServletRedirectGenerator
import uk.co.grahamcox.tcg.authentication.google.*
import uk.co.grahamcox.tcg.user.UserRetriever
import uk.co.grahamcox.tcg.webapp.authentication.FakeGoogleController
import java.net.URI

/**
 * Spring Configuration for Google Authentication
 */
@Configuration
class GoogleAuthenticationConfig {
    @Bean
    @Profile("test", "dev")
    fun fakeGoogleService() = FakeGoogleController()

    @Bean
    fun googleConfig(@Value("\${authentication.google.clientId}") clientId: String,
                     @Value("\${authentication.google.clientSecret}") clientSecret: String,
                     @Value("\${authentication.google.authUrlBase}") authUrlBase: String,
                     @Value("\${authentication.google.tokenUrl}") tokenUrl: String,
                     @Value("\${authentication.google.userProfileUrl}") userProfileUrl: String) =
            GoogleConfig(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    authUrlBase = URI(authUrlBase),
                    tokenUrl = URI(tokenUrl),
                    userProfileUrl = URI(userProfileUrl)
            )

    @Bean
    fun googleAuthenticationProvider(googleConfig: GoogleConfig,
                                     nonceGenerator: NonceGenerator,
                                     userRetriever: UserRetriever): AuthenticationProvider {
        val redirectGenerator = ServletRedirectGenerator("/api/authentication/google/redirect")
        val restTemplate = RestTemplate()
        return GoogleAuthenticationProvider(
                redirectBuilder = GoogleAuthenticationRedirectBuilder(
                        config = googleConfig,
                        nonceGenerator = nonceGenerator,
                        redirectGenerator = redirectGenerator
                ),
                accessTokenRetriever = GoogleAccessTokenRetriever(
                        config = googleConfig,
                        redirectGenerator = redirectGenerator,
                        restTemplate = restTemplate
                ),
                userProfileRetriever = UserProfileRetriever(
                        config = googleConfig,
                        restTemplate = restTemplate
                ),
                userRetriever = userRetriever
        )
    }
}