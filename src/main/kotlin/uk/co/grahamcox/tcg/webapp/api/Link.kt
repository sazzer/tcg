package uk.co.grahamcox.tcg.webapp.api

import java.net.URI

/**
 * Representation of a link to another resource
 * @property href The actual link
 * @property type The media-type of the link target
 */
data class Link(
        val href: URI,
        val type: String?
)