package uk.co.grahamcox.tcg.authentication.google

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

/**
 * Unit tests for [GoogleAuthenticationRedirectBuilder]
 */
class GoogleAuthenticationRedirectBuilderTest {

    @Test
    fun `generate redirect URI`() {
        val testSubject = GoogleAuthenticationRedirectBuilder(
                config = TestData.googleConfig,
                nonceGenerator = mock {
                    on { this.generate() } doReturn "nonce"
                },
                redirectGenerator = mock {
                    on { this.generate() } doReturn URI("http://redirect.example.com")
                }
        )

        val expected = UriComponentsBuilder.fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", "clientId")
                .queryParam("response_type", "code")
                .queryParam("scope", "openid email")
                .queryParam("redirect_uri", "http://redirect.example.com")
                .queryParam("state", "nonce")
                .build()
                .toUri()

        testSubject.buildRedirectDetails().uri.should.equal(expected)
    }

}