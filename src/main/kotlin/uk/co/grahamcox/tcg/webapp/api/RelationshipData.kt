package uk.co.grahamcox.tcg.webapp.api

/**
 * Representation of the Data in a relationship
 * @property type The type of the resource
 * @property id The ID of the resource
 */
data class RelationshipData<out ID>(
        val type: String,
        val id: ID
)