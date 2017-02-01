package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity

/**
 * Translator interface for translating a Model Identity into an API Resource Identity
 * @param MID The ID type of the Model
 * @param RID The ID type of the Resource
 */
interface ResourceIdentityTranslator<in MID : Id, out RID> {
    /**
     * Actually translate the model identity into the resource identity
     * @param input The input to translate
     * @return the translated resource identity
     */
    fun translate(input: Identity<MID>) : ResourceIdentity<RID>
}