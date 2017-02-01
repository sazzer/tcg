package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.Resource

/**
 * Translator interface for translating a Model into an API Resource
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The ID type of the Resource
 * @param RATTR The Attribute type of the Resource
 */
interface ResourceTranslator<in MID : Id, in MDATA, out RID, out RATTR> {
    /**
     * Actually translate the model into the resource
     * @param input The input to translate
     * @return the translated resource
     */
    fun translate(input: Model<MID, MDATA>) : Resource<RID, RATTR>
}