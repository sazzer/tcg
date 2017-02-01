package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.api.Pagination

/**
 * Standard implementation of [PaginationTranslator]
 */
class PaginationTranslatorImpl<in MID : Id, in MDATA> : PaginationTranslator<MID, MDATA> {
    /**
     * Translate the page into the pagination controls
     * @param page The page to translate
     * @return the pagination controls
     */
    override fun translate(page: Page<MID, MDATA>) = Pagination(
            offset = page.offset,
            total = page.totalCount
    )
}