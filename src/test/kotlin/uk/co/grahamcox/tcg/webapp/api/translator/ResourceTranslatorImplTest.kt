package uk.co.grahamcox.tcg.webapp.api.translator

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test

/**
 * Unit test for [ResourceTranslatorImpl]
 */
class ResourceTranslatorImplTest {
    @Test
    fun `test translate model`() {
        val testSubject = ResourceTranslatorImpl<TestId, String, String, String>(
                resourceDataTranslator = mock {
                    on { this.translate(TestData.testModel) } doReturn TestData.resourceData
                }
        )

        testSubject.translate(TestData.testModel).should.equal(TestData.resource)
    }
}