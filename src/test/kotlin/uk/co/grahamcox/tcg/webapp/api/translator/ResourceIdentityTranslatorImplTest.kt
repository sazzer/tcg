package uk.co.grahamcox.tcg.webapp.api.translator

import com.winterbe.expekt.should
import org.junit.Test

/**
 * Unit test for [ResourceIdentityTranslatorImpl]
 */
class ResourceIdentityTranslatorImplTest {
    @Test
    fun `test translate identity`() {
        val testSubject = ResourceIdentityTranslatorImpl<TestId>("testData")
        testSubject.translate(TestData.testIdentity).should.equal(TestData.resourceIdentity)
    }
}