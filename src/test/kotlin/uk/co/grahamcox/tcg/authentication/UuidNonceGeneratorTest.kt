package uk.co.grahamcox.tcg.authentication

import com.winterbe.expekt.should
import org.junit.Test

/**
 * Unit tests for [UuidNonceGenerator]
 */
class UuidNonceGeneratorTest {
    /** The actual class under test */
    private val testSubject = UuidNonceGenerator()

    @Test
    fun `generate a nonce`() {
        testSubject.generate().should.match("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$".toRegex())
    }
}