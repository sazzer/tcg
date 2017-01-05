package uk.co.grahamcox.tcg.webapp.api

/**
 * Pagination controls for a collection of resources
 * @property offset The offset of the first resource returned in the overall collection
 * @property totalCount The total count of resources in the overall collection
 */
data class Pagination(
        val offset: Int,
        val totalCount: Int
)