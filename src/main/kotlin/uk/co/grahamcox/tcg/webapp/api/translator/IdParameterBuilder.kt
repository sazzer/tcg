package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Identity

/**
 * Standard implementation of the [MvcParameterBuilder] to build the parameters as simply the resource ID
 */
class IdParameterBuilder<in MID : Id> : MvcParameterBuilder<Identity<MID>> {
    /**
     * Build the parameters to the method
     * @param input The input to build the parameters from
     * @return the parameters
     */
    override fun buildParameters(input: Identity<MID>) = arrayOf(input.id.id)
}