package uk.co.grahamcox.tcg.webapp.api

/**
 * Representation of a single Resource
 * @property data The resource data
 */
data class Resource<out ID, out ATTR>(
        val data: ResourceData<ID, ATTR>
)