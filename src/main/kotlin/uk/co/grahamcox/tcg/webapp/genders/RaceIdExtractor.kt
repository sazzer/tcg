package uk.co.grahamcox.tcg.webapp.genders

import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.translator.RelatedIdExtractor

/**
 * Extract the ID of the Race that this Gender is related to
 */
class RaceIdExtractor : RelatedIdExtractor<GenderId, GenderData, String> {
    /**
     * Extract the Relationship ID for the given Model
     * @param input The input model to translate
     * @return the appropriate relationship ID
     */
    override fun extractId(input: Model<GenderId, GenderData>): String {
        return input.data.race.id
    }
}