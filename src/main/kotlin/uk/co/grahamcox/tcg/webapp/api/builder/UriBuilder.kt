package uk.co.grahamcox.tcg.webapp.api.builder

import java.net.URI


/**
 * Builder to build a URI of some form for the given input data
 * @param INPUT the type of input data to use
 */
interface UriBuilder<in INPUT> {
    /**
     * Actually build the link
     * @param input The input data to use
     * @return the link
     */
    fun buildUri(input: INPUT) : URI
}