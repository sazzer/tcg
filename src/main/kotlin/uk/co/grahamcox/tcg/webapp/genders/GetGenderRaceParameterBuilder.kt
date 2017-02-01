package uk.co.grahamcox.tcg.webapp.genders

import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.translator.MvcParameterBuilder

/**
 * Parameter Builder for the Race that a Gender relates to
 */
class GetGenderRaceParameterBuilder : MvcParameterBuilder<Model<GenderId, GenderData>> {
    /**
     * Build the parameters to the method
     * @param input The input to build the parameters from
     * @return the parameters
     */
    override fun buildParameters(input: Model<GenderId, GenderData>) = arrayOf(input.data.race.id)
}