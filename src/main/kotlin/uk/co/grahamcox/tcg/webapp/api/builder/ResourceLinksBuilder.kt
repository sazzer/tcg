package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.ResourceLinks

/**
 * Builder to build the Resource Links for a Model
 * @param MID the ID of the Model
 * @param MDATA The Data of the Model
 */
interface ResourceLinksBuilder<in MID : Id, in MDATA> {
    /**
     * Build the Resource Links for the given Model
     * @param model The model to build the links for
     * @return the resource links
     */
    fun buildLinks(model: Model<MID, MDATA>): ResourceLinks
}