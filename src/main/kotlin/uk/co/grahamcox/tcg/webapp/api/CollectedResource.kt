package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonUnwrapped
import java.net.URI

/**
 * Representation of a resource that is part of a [ResourceCollection]
 * @param ID The type to use for the ID
 * @param DATA The type to use for the data
 * @property identity The identity of the resource
 * @property data The payload data of the resource
 * @property links The resource links
 * @property related The related resources, if any
 */
data class CollectedResource<out ID, out DATA>(
        @JsonUnwrapped val identity: ResourceIdentity<ID>,
        val data: DATA,
        val links: ResourceLinks,
        val related: Map<String, Relationship>?
)