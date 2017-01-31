package uk.co.grahamcox.tcg.webapp.api

/**
 * Representation of pagination controls
 * @property offset The offset of the page
 * @property totalCount The total count of records in the entire resultset
 */
data class Pagination(
        val offset: Int,
        val totalCount: Int
)