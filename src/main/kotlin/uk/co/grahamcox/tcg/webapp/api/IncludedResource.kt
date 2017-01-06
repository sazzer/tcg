package uk.co.grahamcox.tcg.webapp.api

/**
 * Representation of a resource
 * @param ID The type to use for the ID
 * @param DATA The type to use for the data
 * @property data The payload data of the resource
 * @property links The resource links
 */
data class IncludedResource<out DATA>(
        val data: DATA,
        val links: ResourceLinks?
)