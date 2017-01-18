package uk.co.grahamcox.tcg.webapp.acceptance.authentication

import com.winterbe.expekt.should
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import uk.co.grahamcox.tcg.webapp.acceptance.AcceptanceTestBase
import uk.co.grahamcox.tcg.webapp.acceptance.Requester

/**
 * Acceptance Tests for authentication with an unknown provider
 */
class UnknownAuthenticationProviderIT : AcceptanceTestBase() {
    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    @Test
    fun `start authentication with an unknown provider`() {
        requester.get("/api/authentication/unknown/start").let { response ->
            response.statusCode.should.equal(HttpStatus.NOT_FOUND)
        }
    }

    @Test
    fun `callback from an unknown provider`() {
        requester.get("/api/authentication/unknown/redirect").let { response ->
            response.statusCode.should.equal(HttpStatus.NOT_FOUND)
        }
    }
}