package uk.co.grahamcox.tcg.model

/**
 * Representation of a single sort to perform
 * @param SORT The type to use for the sort field
 * @property sort The sort field
 * @property order The sort order for this field
 */
data class Sort<SORT : Enum<SORT>>(
        val sort: SORT,
        val order: SortOrder
)