package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.ResourceLinks

/**
 * Translator interface for translating a Model into an API Resource Links
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The ID type of the Resource
 * @param RATTR The Attribute type of the Resource
 */
interface ResourceLinksTranslator<in MID : Id, in MDATA> {
    /**
     * Actually translate the model into the resource links
     * @param input The input to translate
     * @return the translated resource links
     */
    fun translate(input: Model<MID, MDATA>) : ResourceLinks
}