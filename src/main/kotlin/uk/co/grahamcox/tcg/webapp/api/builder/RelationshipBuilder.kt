package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.Relationship

/**
 * Builder to build a Relationship for a given Model
 * @param MID The ID for the Model
 * @param MDATA The Data for the Model
 */
interface RelationshipBuilder<in MID : Id, in MDATA> {
    /**
     * Build the Relationship for the given Model
     * @param model The model to build the relationship for
     * @return the relationship
     */
    fun buildRelationship(model: Model<MID, MDATA>) : Relationship
}