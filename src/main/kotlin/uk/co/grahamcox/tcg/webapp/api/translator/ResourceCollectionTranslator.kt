package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.api.ResourceCollection

/**
 * Translator interface for translating a Page into an API Resource Collection
 * @param MID The ID type of the Models
 * @param MDATA The Data type of the Models
 * @param RID The ID type of the Resources
 * @param RATTR The Attribute type of the Resources
 */
interface ResourceCollectionTranslator<in MID : Id, in MDATA, out RID, out RATTR> {
    /**
     * Translate the given page into a Resource collection
     * @param input The input to translate
     * @return the translated resource collection
     */
    fun translate(input: Page<MID, MDATA>) : ResourceCollection<RID, RATTR>
}