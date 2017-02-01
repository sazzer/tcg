package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.api.Pagination

/**
 * Translator for building pagination controls from a page of results
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 */
interface PaginationTranslator<in MID : Id, in MDATA> {
    /**
     * Translate the page into the pagination controls
     * @param page The page to translate
     * @return the pagination controls
     */
    fun translate(page: Page<MID, MDATA>) : Pagination
}