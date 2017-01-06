package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.webapp.api.Link

/**
 * Builder to build a Link of some form for the given input data
 * @param INPUT the type of input data to use
 */
interface LinkBuilder<in INPUT> {
    /**
     * Actually build the link
     * @param input The input data to use
     * @return the link
     */
    fun buildLink(input: INPUT) : Link
}