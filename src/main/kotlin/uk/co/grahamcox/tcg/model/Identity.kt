package uk.co.grahamcox.tcg.model

import java.time.Instant

/**
 * The identity of a resource
 * @param ID the type to use for the ID
 * @property id The ID of the resource
 * @property created When the resource was created
 * @property updated When the resource was updated
 * @property version The version of the resource
 */
data class Identity<out ID : Id>(
        val id: ID,
        val created: Instant,
        val updated: Instant,
        val version: String
)