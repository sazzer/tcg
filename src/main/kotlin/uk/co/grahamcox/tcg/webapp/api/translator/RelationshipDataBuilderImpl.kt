package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.RelationshipData

/**
 * Standard implementation of the [RelationshipDataBuilder]
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The type of the relationship ID
 */
class RelationshipDataBuilderImpl<in MID : Id, in MDATA, out RID>(
        private val relationshipIdentityBuilder: RelationshipIdentityBuilder<MID, MDATA, RID>
) : RelationshipDataBuilder<MID, MDATA, RID> {
    /**
     * Build the data for a relationship
     * @param input The input model
     * @return the relationship data
     */
    override fun buildRelationshipData(input: Model<MID, MDATA>) = RelationshipData(
            id = relationshipIdentityBuilder.buildRelationshipIdentity(input)
    )
}