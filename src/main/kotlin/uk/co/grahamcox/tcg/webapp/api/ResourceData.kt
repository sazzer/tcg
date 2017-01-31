package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonUnwrapped

/**
 * Representation of the Data in a single Resource
 * @property id The ID of the resource
 * @property attributes The attributes of the resource
 * @property links The resource links
 * @property relationships Any relationships for the resource
 */
data class ResourceData<out ID, out ATTR>(
        @JsonUnwrapped val id: ResourceIdentity<ID>,
        val attributes: ATTR,
        val links: ResourceLinks,
        val relationships: Map<String, Relationship>?
)