package uk.co.grahamcox.tcg.webapp.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import uk.co.grahamcox.tcg.authentication.AuthenticationProviderRegistry
import uk.co.grahamcox.tcg.authentication.UuidNonceGenerator
import uk.co.grahamcox.tcg.authentication.google.GoogleAuthenticationProvider
import uk.co.grahamcox.tcg.webapp.authentication.AuthenticationController

/**
 * Spring config for authentication
 */
@Configuration
@Import(GoogleAuthenticationConfig::class)
class AuthenticationConfig {
    @Bean
    fun authenticationController(authenticationProviderRegistry: AuthenticationProviderRegistry) =
            AuthenticationController(authenticationProviderRegistry)

    @Bean
    fun authenticationProviderRegistry(google: GoogleAuthenticationProvider) =
            AuthenticationProviderRegistry(
                    mapOf(
                            "google" to google
                    )
            )

    @Bean
    fun nonceGenerator() = UuidNonceGenerator()
}