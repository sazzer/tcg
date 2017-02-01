package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity

/**
 * Standard implementation of the [RelationshipIdentityBuilder]
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The type of the relationship ID
 * @property type The type of the Related Resource
 * @property relationshipIdExtractor The means to get the ID of the related resource
 */
class RelationshipIdentityBuilderImpl<in MID : Id, in MDATA, out RID>(
        private val type: String,
        private val relationshipIdExtractor: RelatedIdExtractor<MID, MDATA, RID>
) : RelationshipIdentityBuilder<MID, MDATA, RID> {
    /**
     * Build the relationship identity
     * @param input The input to get the data from
     * @return the relationship identity
     */
    override fun buildRelationshipIdentity(input: Model<MID, MDATA>) = ResourceIdentity(
            type = type,
            id = relationshipIdExtractor.extractId(input)
    )
}