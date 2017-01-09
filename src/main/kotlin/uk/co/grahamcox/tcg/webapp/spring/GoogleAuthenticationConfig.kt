package uk.co.grahamcox.tcg.webapp.spring

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import uk.co.grahamcox.tcg.authentication.NonceGenerator
import uk.co.grahamcox.tcg.authentication.ServletRedirectGenerator
import uk.co.grahamcox.tcg.authentication.google.GoogleAuthenticationProvider
import uk.co.grahamcox.tcg.authentication.google.GoogleConfig
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
    fun googleAuthenticationProvider(googleConfig: GoogleConfig, nonceGenerator: NonceGenerator) =
            GoogleAuthenticationProvider(googleConfig, nonceGenerator, ServletRedirectGenerator("/api/authentication/google/redirect"))
}