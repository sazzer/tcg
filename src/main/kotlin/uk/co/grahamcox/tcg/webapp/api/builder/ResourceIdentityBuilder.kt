package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity

/**
 * Builder to build the API Resource Identity of the model with the given Identity
 * @param MID The ID of the Model
 * @param RID The ID of the API Resource
 */
interface ResourceIdentityBuilder<in MID : Id, out RID> {
    /**
     * Build the API Resource Identity from the given Model Identity
     * @param identity The model identity to build the resource identity from
     * @return the API Resource Identity
     */
    fun buildResourceIdentity(identity: Identity<MID>) : ResourceIdentity<RID>
}