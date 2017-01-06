package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity

/**
 * Standard implementation fo the Resource Identity Builder
 * @param MID The ID of the Model
 * @property type The type of the API Resource
 */
class ResourceIdentityBuilderImpl<in MID : Id>(
        private val type: String
) : ResourceIdentityBuilder<MID, String> {
    /**
     * Build the API Resource Identity from the given Model Identity
     * @param identity The model identity to build the resource identity from
     * @return the API Resource Identity
     */
    override fun buildResourceIdentity(identity: Identity<MID>) = ResourceIdentity(
            type = type,
            id = identity.id.id
    )
}