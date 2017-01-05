package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonAnyGetter

/**
 * Links to other resources at the resource collection level
 * @property self The self link
 * @property first The first page of the collection
 * @property previous The previous page of the collection
 * @property next The next page of the collection
 * @property last The last page of the collection
 * @property otherLinks Any other links
 */
data class ResourceCollectionLinks(
        val self: Link,
        val first: Link?,
        val previous: Link?,
        val next: Link?,
        val last: Link?,
        @get:JsonAnyGetter val otherLinks: Map<String, Link>?
)