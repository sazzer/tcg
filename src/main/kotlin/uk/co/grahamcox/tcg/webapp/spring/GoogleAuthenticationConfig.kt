package uk.co.grahamcox.tcg.webapp.spring

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.tcg.authentication.google.GoogleAuthenticationProvider
import uk.co.grahamcox.tcg.authentication.google.GoogleConfig
import java.net.URI

/**
 * Spring Configuration for Google Authentication
 */
@Configuration
class GoogleAuthenticationConfig {
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
    fun googleAuthenticationProvider() = GoogleAuthenticationProvider()
}