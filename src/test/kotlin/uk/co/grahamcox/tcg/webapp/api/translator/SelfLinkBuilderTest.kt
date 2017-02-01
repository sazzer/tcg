package uk.co.grahamcox.tcg.webapp.api.translator

import com.winterbe.expekt.should
import org.junit.Rule
import org.junit.Test
import uk.co.grahamcox.tcg.CurrentRequestRule
import java.net.URI

/**
 * Unit tests for [SelfLinkBuilder]
 */
class SelfLinkBuilderTest {
    @JvmField @Rule
    val currentRequest = CurrentRequestRule("http://test.example.com/api/attributes/def")

    @Test
    fun `build link`() {
        val testSubject = SelfLinkBuilder<String>()
        testSubject.translate("hello").should.equal(URI("http://test.example.com/api/attributes/def"))
    }
}