package uk.co.grahamcox.tcg.webapp.api

import java.net.URI

/**
 * Representation of a collection of resources
 * @property pagination The pagination controls for this page of resources
 * @property data The data that makes up this page of resources
 * @property links The links for this page of resources
 * @property included Any included related resources
 */
data class ResourceCollection(
        val pagination: Pagination,
        val data: List<CollectedResource<*, *>>,
        val links: ResourceCollectionLinks,
        val included: Map<URI, IncludedResource<*, *>>?
)