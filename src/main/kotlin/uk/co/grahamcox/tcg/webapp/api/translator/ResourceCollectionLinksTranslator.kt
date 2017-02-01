package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.api.ResourceCollectionLinks
import uk.co.grahamcox.tcg.webapp.api.ResourceLinks

/**
 * Translator interface for translating a Page into an API Resource Collection Links
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 */
interface ResourceCollectionLinksTranslator<in MID : Id, in MDATA> {
    /**
     * Actually translate the model into the resource links
     * @param input The input to translate
     * @return the translated resource links
     */
    fun translate(input: Page<MID, MDATA>) : ResourceCollectionLinks
}