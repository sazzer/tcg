package uk.co.grahamcox.tcg.webapp.api.translator

/**
 * Builder to build the parameters to an MVC Controller method
 * @param INPUT the input type
 */
interface MvcParameterBuilder<in INPUT> {
    /**
     * Build the parameters to the method
     * @param input The input to build the parameters from
     * @return the parameters
     */
    fun buildParameters(input: INPUT): Array<*>
}