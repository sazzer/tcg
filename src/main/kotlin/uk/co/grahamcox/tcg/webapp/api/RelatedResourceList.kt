package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonValue

/**
 * Relationship that is actually a collection of multiple links
 * @property relatedResources The actual list of relationships
 */
data class RelatedResourceList(
        @get:JsonValue val relatedResources: List<RelatedResource<*, *>>
) : Relationship