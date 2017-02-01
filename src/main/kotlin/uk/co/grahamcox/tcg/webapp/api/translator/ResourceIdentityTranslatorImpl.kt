package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity

/**
 * Standard implementation of the [ResourceIdentityTranslator]
 * @property type The type of resource we are translating
 */
class ResourceIdentityTranslatorImpl<in MID : Id>(
        private val type: String
) : ResourceIdentityTranslator<MID, String> {
    /**
     * Actually translate the model identity into the resource identity
     * @param input The input to translate
     * @return the translated resource identity
     */
    override fun translate(input: Identity<MID>) = ResourceIdentity(type, input.id.id)
}