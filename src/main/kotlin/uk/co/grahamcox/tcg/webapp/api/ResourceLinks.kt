package uk.co.grahamcox.tcg.webapp.api

import java.net.URI

/**
 * Links to a single Resource
 * @property self The self link
 */
data class ResourceLinks(
        val self: URI
)