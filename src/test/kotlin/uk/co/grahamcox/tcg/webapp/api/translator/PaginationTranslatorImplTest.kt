package uk.co.grahamcox.tcg.webapp.api.translator

import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.api.Pagination

/**
 * Unit test for [PaginationTranslatorImpl]
 */
class PaginationTranslatorImplTest {
    @Test
    fun `translate page`() {
        val page = Page<TestId, String>(
                listOf(),
                offset = 5,
                totalCount = 20
        )
        val testSubject = PaginationTranslatorImpl<TestId, String>()
        testSubject.translate(page).should.equal(Pagination(5, 20))
    }
}