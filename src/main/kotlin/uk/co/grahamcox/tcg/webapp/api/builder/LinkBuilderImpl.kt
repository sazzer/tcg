package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.webapp.api.Link

/**
 * Standard implementation of the Link Builder
 * @param INPUT the type of input data to use
 * @property uriBuilder The mechanism to build the URI for the link
 * @property type The media-type of the link target
 */
class LinkBuilderImpl<in INPUT>(
        private val uriBuilder: UriBuilder<INPUT>,
        private val type: String?
) : LinkBuilder<INPUT> {
    /**
     * Actually build the link
     * @param input The input data to use
     * @return the link
     */
    override fun buildLink(input: INPUT) = Link(
            href = uriBuilder.buildUri(input),
            type = type
    )
}