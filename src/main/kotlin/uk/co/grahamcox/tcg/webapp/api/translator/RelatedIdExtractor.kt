package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model

/**
 * Mechanism to get the related ID for some resource relationship
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The type of the relationship ID
 */
interface RelatedIdExtractor<in MID : Id, in MDATA, out RID> {
    /**
     * Extract the Relationship ID for the given Model
     * @param input The input model to translate
     * @return the appropriate relationship ID
     */
    fun extractId(input: Model<MID, MDATA>) : RID
}