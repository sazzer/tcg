package uk.co.grahamcox.tcg.authentication

import com.winterbe.expekt.should
import org.junit.Rule
import org.junit.Test
import uk.co.grahamcox.tcg.CurrentRequestRule
import java.net.URI

/**
 * Unit tests for [ServletRedirectGenerator]
 */
class ServletRedirectGeneratorTest {
    /** Set up the current request */
    @JvmField @Rule
    val currentRequestRule = CurrentRequestRule("https://www.example.com:8443/api/authentication/google/start")

    /** The test subject */
    private val testSubject = ServletRedirectGenerator("/api/authentication/google/redirect")

    @Test
    fun `generates the correct redirect`() {
        testSubject.generate().should.equal(URI("https://www.example.com:8443/api/authentication/google/redirect"))
    }
}