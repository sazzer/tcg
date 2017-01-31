package uk.co.grahamcox.tcg.webapp.api

/**
 * Identity of a resource
 * @property type The type of the resource
 * @property id The ID of the resource
 */
data class ResourceIdentity<out ID>(
        val type: String,
        val id: ID
)