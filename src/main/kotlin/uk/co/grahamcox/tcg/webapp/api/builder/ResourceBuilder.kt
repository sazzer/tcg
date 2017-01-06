package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.Resource

/**
 * Mechanism to build a single Resource from a single Mode
 * @param MID the ID of the Model
 * @param MDATA The data of the Model
 * @param RID The ID of the API Resource
 * @param RDATA The Data of the API Resource
 */
interface ResourceBuilder<in MID : Id, in MDATA, out RID, out RDATA> {
    /**
     * Build a Resource representation of the given Model data
     * @param input The input model to build the resource from
     * @return the API resource
     */
    fun buildResource(input: Model<MID, MDATA>) : Resource<RID, RDATA>
}