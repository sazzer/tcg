package uk.co.grahamcox.tcg.webapp.api.translator

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity

/**
 * Unit tests for [RelationshipIdentityBuilderImpl]
 */
class RelationshipIdentityBuilderImplTest {
    @Test
    fun `build relationship identity`() {
        val testSubject = RelationshipIdentityBuilderImpl<TestId, String, String>(
                type = "related",
                relationshipIdExtractor = mock {
                    on { this.extractId(TestData.testModel) } doReturn "identifier"
                }
        )

        testSubject.buildRelationshipIdentity(TestData.testModel).should.equal(ResourceIdentity(
                type = "related",
                id = "identifier"
        ))
    }
}