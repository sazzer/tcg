package uk.co.grahamcox.tcg.webapp.api.builder

import com.winterbe.expekt.should
import org.junit.Test

/**
 * Unit tests for [ResourceIdentityBuilderImpl]
 */
class ResourceIdentityBuilderImplTest {
    /**
     * The test subject
     */
    private val testSubject = ResourceIdentityBuilderImpl<TestId>("test")

    @Test
    fun `built Resource Identity has correct type`() {
        testSubject.buildResourceIdentity(TestData.identity).type.should.equal("test")
    }

    @Test
    fun `built Resource Identity has correct ID`() {
        testSubject.buildResourceIdentity(TestData.identity).id.should.equal(TestData.rawID)
    }

}