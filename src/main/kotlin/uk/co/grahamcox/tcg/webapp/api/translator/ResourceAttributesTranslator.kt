package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.Resource

/**
 * Translator interface for translating a Model into an API Resource Attributes
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The ID type of the Resource
 * @param RATTR The Attribute type of the Resource
 */
interface ResourceAttributesTranslator<in MID : Id, in MDATA, out RATTR> {
    /**
     * Actually translate the model into the resource attributes
     * @param input The input to translate
     * @return the translated resource attributes
     */
    fun translate(input: Model<MID, MDATA>) : RATTR
}