package uk.co.grahamcox.tcg.webapp.api

/**
 * Representation of a single Resource
 * @property links The links to the collection
 * @property pagination The pagination controls
 * @property data The resource data
 */
data class ResourceCollection<out ID, out ATTR>(
        val links: ResourceCollectionLinks,
        val pagination: Pagination,
        val data: List<ResourceData<ID, ATTR>>
)