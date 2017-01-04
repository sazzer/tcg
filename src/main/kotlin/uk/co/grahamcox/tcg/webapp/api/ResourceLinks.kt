package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonAnyGetter

/**
 * Links to other resources at the individual resource level
 * @property self The self link
 * @property otherLinks Any other links
 */
data class ResourceLinks(
        val self: Link,
        @get:JsonAnyGetter val otherLinks: Map<String, Link>?
)