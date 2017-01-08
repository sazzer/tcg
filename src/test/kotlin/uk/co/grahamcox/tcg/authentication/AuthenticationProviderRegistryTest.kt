package uk.co.grahamcox.tcg.authentication

import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test

/**
 * Unit tests for [AuthenticationProviderRegistry]
 */
class AuthenticationProviderRegistryTest {
    private val googleAuthenticationProvider = mock<AuthenticationProvider>()

    private val testSubject = AuthenticationProviderRegistry(mapOf(
            "google" to googleAuthenticationProvider,
            "facebook" to mock<AuthenticationProvider>(),
            "twitter" to mock<AuthenticationProvider>()
    ))

    @Test
    fun `get the provider names`() {

        testSubject.getProviderNames().should.equal(listOf("facebook", "google", "twitter"))
    }

    @Test
    fun `get a known provider`() {
        testSubject.getProvider("google").should.equal(googleAuthenticationProvider)
    }
    @Test
    fun `get an unknown provider`() {
        testSubject.getProvider("unknown").should.be.`null`
    }
}