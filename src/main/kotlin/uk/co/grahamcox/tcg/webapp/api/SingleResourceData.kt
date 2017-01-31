package uk.co.grahamcox.tcg.webapp.api

/**
 * Representation of the Data in a single Resource
 * @property type The type of the resource
 * @property id The ID of the resource
 * @property attributes The attributes of the resource
 * @property links The resource links
 * @property relationships Any relationships for the resource
 */
data class SingleResourceData<out ID, out ATTR>(
        val type: String,
        val id: ID,
        val attributes: ATTR,
        val links: ResourceLinks,
        val relationships: Map<String, Relationship>?
) : ResourceData