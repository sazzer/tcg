package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.ResourceIdentity

/**
 * Mechanism to build the identity to a related resource
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The type of the relationship ID
 */
interface RelationshipIdentityBuilder<in MID : Id, in MDATA, out RID> {
    /**
     * Build the relationship identity
     * @param input The input to get the data from
     * @return the relationship identity
     */
    fun buildRelationshipIdentity(input: Model<MID, MDATA>) : ResourceIdentity<RID>
}