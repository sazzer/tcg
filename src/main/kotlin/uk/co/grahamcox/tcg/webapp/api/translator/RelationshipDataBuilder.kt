package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.RelationshipData

/**
 * Mechanism to build the data for a relationship
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The type of the relationship ID
 */
interface RelationshipDataBuilder<in MID : Id, in MDATA, out RID> {
    /**
     * Build the data for a relationship
     * @param input The input model
     * @return the relationship data
     */
    fun buildRelationshipData(input: Model<MID, MDATA>) : RelationshipData<RID>
}