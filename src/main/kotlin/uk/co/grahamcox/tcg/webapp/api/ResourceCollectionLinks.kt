package uk.co.grahamcox.tcg.webapp.api

import java.net.URI

/**
 * Links to a Resource Collection
 * @property self The self link
 */
data class ResourceCollectionLinks(
        val self: URI
)