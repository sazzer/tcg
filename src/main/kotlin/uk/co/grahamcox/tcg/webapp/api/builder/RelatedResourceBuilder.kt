package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.RelatedResource

/**
 * Builder to build a single related resource for a model
 * @param MID The ID for the Model
 * @param MDATA The Data for the Model
 * @param RID The ID type of the related resource
 * @param RDATA The data type of the relationship, if any
 */
class RelatedResourceBuilder<in MID : Id, in MDATA, out RID, out RDATA> : RelationshipBuilder<MID, MDATA> {
    /**
     * Build the Relationship for the given Model
     * @param model The model to build the relationship for
     * @return the relationship
     */
    override fun buildRelationship(model: Model<MID, MDATA>): RelatedResource<RID, RDATA> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}