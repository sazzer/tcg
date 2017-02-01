package uk.co.grahamcox.tcg.webapp.api.translator

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.api.ResourceCollectionLinks
import java.net.URI

/**
 * Unit tests for [ResourceCollectionLinksTranslatorImpl]
 */
class ResourceCollectionLinksTranslatorImplTest {
    @Test
    fun `translate page`() {
        val page = Page<TestId, String>(
                listOf(),
                offset = 5,
                totalCount = 20
        )
        val testSubject = ResourceCollectionLinksTranslatorImpl<TestId, String>(
                selfTranslator = mock {
                    on { this.translate(page) } doReturn URI("http://test.example.com")
                }
        )

        testSubject.translate(page).should.equal(ResourceCollectionLinks(
                self = URI("http://test.example.com")
        ))
    }
}