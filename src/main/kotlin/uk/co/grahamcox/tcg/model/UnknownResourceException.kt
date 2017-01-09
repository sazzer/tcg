package uk.co.grahamcox.tcg.model

/**
 * Indication that a resource was not found
 * @property id The ID of the resource
 */
class UnknownResourceException(val id: Id) : RuntimeException("Requested resource was not found: ${id}")