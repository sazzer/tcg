package uk.co.grahamcox.tcg.webapp.api

/**
 * The identity of an API resource
 * @param ID The type to use for the ID
 * @property type The type of the resource
 * @property id The ID of the resource
 */
data class ResourceIdentity<out ID>(
        val type: String,
        val id: ID
)