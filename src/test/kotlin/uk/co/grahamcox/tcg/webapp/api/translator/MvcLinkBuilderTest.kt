package uk.co.grahamcox.tcg.webapp.api.translator

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Rule
import org.junit.Test
import uk.co.grahamcox.tcg.CurrentRequestRule
import uk.co.grahamcox.tcg.webapp.attributes.AttributesController
import java.net.URI

/**
 * Unit tests for [MvcLinkBuilder]
 */
class MvcLinkBuilderTest {
    @JvmField @Rule
    val currentRequest = CurrentRequestRule("http://test.example.com/api/attributes/def")

    @Test(expected = IllegalArgumentException::class)
    fun `invalid method`() {
        MvcLinkBuilder(AttributesController::class.java, "unknown", mock<MvcParameterBuilder<String>> {  })
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invalid arguments`() {
        val testSubject = MvcLinkBuilder(
                AttributesController::class.java,
                "getAttribute",
                mock<MvcParameterBuilder<String>> {
                    on { this.buildParameters("hello") } doReturn arrayOf(1, 2, 3)
                }
        )

        testSubject.translate("hello")
    }

    @Test
    fun `valid arguments`() {
        val testSubject = MvcLinkBuilder(
                AttributesController::class.java,
                "getAttribute",
                mock<MvcParameterBuilder<String>> {
                    on { this.buildParameters("hello") } doReturn arrayOf("abc")
                }
        )

        testSubject.translate("hello").should.equal(URI("http://test.example.com/api/attributes/abc"))
    }
}