package uk.co.grahamcox.tcg.webapp.spring

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import uk.co.grahamcox.tcg.authentication.AuthenticationProviderRegistry
import uk.co.grahamcox.tcg.authentication.UuidNonceGenerator
import uk.co.grahamcox.tcg.authentication.google.GoogleAuthenticationProvider
import uk.co.grahamcox.tcg.authentication.token.AccessTokenEncoder
import uk.co.grahamcox.tcg.authentication.token.AccessTokenGeneratorImpl
import uk.co.grahamcox.tcg.authentication.token.JwtAccessTokenEncoder
import uk.co.grahamcox.tcg.webapp.authentication.AccessTokenInterceptor
import uk.co.grahamcox.tcg.webapp.authentication.AccessTokenStore
import uk.co.grahamcox.tcg.webapp.authentication.AuthenticationController
import java.time.Clock
import java.time.Duration

/**
 * Spring config for authentication
 */
@Configuration
@Import(GoogleAuthenticationConfig::class)
open class AuthenticationConfig {
    @Bean
    open fun accessTokenEncoder(clock: Clock,
                           @Value("\${authentication.accessToken.signingKey}") jwtSecret: String,
                           @Value("\${authentication.accessToken.expiry}") expiry: String) = JwtAccessTokenEncoder(
            jwtSecret = jwtSecret,
            clock = clock,
            expiry = Duration.parse(expiry))

    @Bean
    open fun authenticationController(authenticationProviderRegistry: AuthenticationProviderRegistry,
                                 accessTokenEncoder: AccessTokenEncoder) =
            AuthenticationController(
                    authenticationProviderRegistry = authenticationProviderRegistry,
                    accessTokenGenerator = AccessTokenGeneratorImpl(),
                    accessTokenEncoder = accessTokenEncoder)

    @Bean
    open fun authenticationProviderRegistry(google: GoogleAuthenticationProvider) =
            AuthenticationProviderRegistry(
                    mapOf(
                            "google" to google
                    )
            )

    @Bean
    open fun nonceGenerator() = UuidNonceGenerator()

    @Bean
    open fun accessTokenStore() = AccessTokenStore()

    @Bean
    open fun accessTokenInterceptor(accessTokenEncoder: AccessTokenEncoder,
                                    accessTokenStore: AccessTokenStore) = AccessTokenInterceptor(accessTokenEncoder, accessTokenStore)
}