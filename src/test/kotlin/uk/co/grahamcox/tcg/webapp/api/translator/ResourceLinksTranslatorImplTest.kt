package uk.co.grahamcox.tcg.webapp.api.translator

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import java.net.URI

/**
 * Unit test for [ResourceLinksTranslatorImpl]
 */
class ResourceLinksTranslatorImplTest {
    @Test
    fun `translate resource links`() {
        val testSubject = ResourceLinksTranslatorImpl<TestId, String>(
                selfTranslator = mock {
                    on { this.translate(TestData.testIdentity) } doReturn URI("http://test.example.com")
                }
        )

        testSubject.translate(TestData.testModel).should.equal(TestData.resourceLinks)
    }
}