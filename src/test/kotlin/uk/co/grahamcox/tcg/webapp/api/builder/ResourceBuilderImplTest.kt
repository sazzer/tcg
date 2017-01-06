package uk.co.grahamcox.tcg.webapp.api.builder

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.webapp.api.Link
import uk.co.grahamcox.tcg.webapp.api.Relationship
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity
import uk.co.grahamcox.tcg.webapp.api.ResourceLinks
import java.math.BigDecimal
import java.net.URI

/**
 * Unit Tests for [ResourceBuilderImpl]
 */
class ResourceBuilderImplTest {
    /** A Resource Identity to use */
    val resourceIdentity = ResourceIdentity(
            type = "test",
            id = "abc"
    )

    /** The Resource Links to use */
    val resourceLinks = ResourceLinks(
            self = Link(
                    href = URI("http://self.example.com"),
                    type = null
            ),
            otherLinks = null
    )

    /** The relationship to use */
    val relationship = mock<Relationship>()

    @Test
    fun `build a simple resource`() {
        val testSubject = ResourceBuilderImpl<TestId, String, String, BigDecimal>(
                identityBuilder = mock {
                    on { this.buildResourceIdentity(TestData.identity) } doReturn resourceIdentity
                },
                dataTranslator = mock {
                    on { this.translateResourceData(TestData.model) } doReturn BigDecimal.TEN
                },
                linksBuilder = mock {
                    on { this.buildLinks(TestData.model) } doReturn resourceLinks
                },
                relationshipBuilders = null
        )

        val resource = testSubject.buildResource(TestData.model)

        resource.identity.should.equal(resourceIdentity)
        resource.data.should.equal(BigDecimal.TEN)
        resource.links.should.equal(resourceLinks)
        resource.included.should.be.`null`
        resource.related.should.be.`null`
    }

    @Test
    fun `build a resource with a relationship`() {
        val testSubject = ResourceBuilderImpl<TestId, String, String, BigDecimal>(
                identityBuilder = mock {
                    on { this.buildResourceIdentity(TestData.identity) } doReturn resourceIdentity
                },
                dataTranslator = mock {
                    on { this.translateResourceData(TestData.model) } doReturn BigDecimal.TEN
                },
                linksBuilder = mock {
                    on { this.buildLinks(TestData.model) } doReturn resourceLinks
                },
                relationshipBuilders = mapOf(
                        "related" to mock {
                            on { this.buildRelationship(TestData.model) } doReturn relationship
                        }
                )
        )

        val resource = testSubject.buildResource(TestData.model)

        resource.identity.should.equal(resourceIdentity)
        resource.data.should.equal(BigDecimal.TEN)
        resource.links.should.equal(resourceLinks)
        resource.included.should.be.`null`
        resource.related.should.have.size(1)
        resource.related?.get("related").should.equal(relationship)
    }
}