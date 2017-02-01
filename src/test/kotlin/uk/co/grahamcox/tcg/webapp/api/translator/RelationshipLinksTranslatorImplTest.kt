package uk.co.grahamcox.tcg.webapp.api.translator

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.webapp.api.RelationshipLinks
import java.net.URI

/**
 * Unit tests for [RelationshipLinksTranslatorImpl]
 */
class RelationshipLinksTranslatorImplTest {
    @Test
    fun `translate relationship links`() {
        val testSubject = RelationshipLinksTranslatorImpl<TestId, String>(
                selfTranslator = mock {
                    on { this.translate(TestData.testModel) } doReturn URI("http://test.example.com")
                }
        )

        testSubject.translate(TestData.testModel).should.equal(RelationshipLinks(
            self = URI("http://test.example.com")
        ))
    }
}