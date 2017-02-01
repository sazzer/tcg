package uk.co.grahamcox.tcg.webapp.api.translator

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.webapp.api.RelationshipData
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity

/**
 * Unit tests fo r[RelationshipDataBuilderImpl]
 */
class RelationshipDataBuilderImplTest {
    @Test
    fun `build relationship data`() {
        val testSubject = RelationshipDataBuilderImpl<TestId, String, String>(
                relationshipIdentityBuilder = mock {
                    on { this.buildRelationshipIdentity(TestData.testModel) } doReturn ResourceIdentity(
                            type = "related",
                            id = "identity"
                    )
                }
        )

        testSubject.buildRelationshipData(TestData.testModel).should.equal(RelationshipData(
                id = ResourceIdentity(
                        type = "related",
                        id = "identity"
                )
        ))
    }
}