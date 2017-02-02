package uk.co.grahamcox.tcg.webapp

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.PaginationModel

/**
 * Translator to translate a Page of model objects into an API Page object
 * @param MID the ID of the Model
 * @param MDATA The Data of the Model
 * @param API The API object
 */
class PageTranslator<in MID : Id, in MDATA, out API>(
        private val modelTranslator: ModelTranslator<MID, MDATA, API>
) {
    /**
     * Actually translate the given page of data
     * @param input The page to translate
     * @return the translated page
     */
    fun translate(input: Page<MID, MDATA>) =
            PageModel()
                    .withPagination(PaginationModel()
                            .withOffset(input.offset.toLong())
                            .withTotal(input.totalCount.toLong()))
                    .withEntries(input.contents.map { modelTranslator.translate(it) })
}