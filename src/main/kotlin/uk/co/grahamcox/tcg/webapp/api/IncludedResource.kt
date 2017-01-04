package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonUnwrapped

/**
 * Representation of a resource
 * @param ID The type to use for the ID
 * @param DATA The type to use for the data
 * @property identity The identity of the resource
 * @property data The payload data of the resource
 * @property links The resource links
 */
data class IncludedResource<out ID, out DATA>(
        @JsonUnwrapped val identity: ResourceIdentity<ID>,
        val data: DATA,
        val links: ResourceLinks?
)