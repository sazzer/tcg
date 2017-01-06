package uk.co.grahamcox.tcg.webapp.api.builder

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.webapp.api.Link
import java.net.URI

/**
 * Unit tests for [ResourceLinksBuilderImpl]
 */
class ResourceLinksBuilderImplTest {
    @Test
    fun `self links build correctly`() {
        val testSubject = ResourceLinksBuilderImpl<TestId, String>(
                selfLinkBuilder = mock {
                    on { this.buildLink(TestData.model) } doReturn Link(href = URI("http://self.example.com"), type = null)
                }
        )

        testSubject.buildLinks(TestData.model).self.should.equal(Link(href = URI("http://self.example.com"), type = null))
    }

    @Test
    fun `no other links works correctly`() {
        val testSubject = ResourceLinksBuilderImpl<TestId, String>(
                selfLinkBuilder = mock {
                    on { this.buildLink(TestData.model) } doReturn Link(href = URI("http://self.example.com"), type = null)
                }
        )

        testSubject.buildLinks(TestData.model).otherLinks.should.be.empty
    }

    @Test
    fun `empty other links works correctly `() {
        val testSubject = ResourceLinksBuilderImpl<TestId, String>(
                selfLinkBuilder = mock {
                    on { this.buildLink(TestData.model) } doReturn Link(href = URI("http://self.example.com"), type = null)
                },
                otherLinkBuilders = mapOf()
        )

        testSubject.buildLinks(TestData.model).otherLinks.should.be.empty
    }

    @Test
    fun `other links build correctly`() {
        val testSubject = ResourceLinksBuilderImpl<TestId, String>(
                selfLinkBuilder = mock {
                    on { this.buildLink(TestData.model) } doReturn Link(href = URI("http://self.example.com"), type = null)
                },
                otherLinkBuilders = mapOf(
                        "other" to mock {
                            on { this.buildLink(TestData.model) } doReturn Link(href = URI("http://other.example.com"), type = null)
                        }
                )
        )

        testSubject.buildLinks(TestData.model).otherLinks.should.be.size(1)
        testSubject.buildLinks(TestData.model).otherLinks?.get("other").should.equal(Link(href = URI("http://other.example.com"), type = null))
    }
}