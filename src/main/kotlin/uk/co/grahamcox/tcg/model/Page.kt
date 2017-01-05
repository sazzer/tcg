package uk.co.grahamcox.tcg.model

/**
 * Representation of a page of resources
 * @param ID the type to use for the ID of a resource
 * @param DATA The type to use for the data of a resource
 * @property contents The contents of the page
 * @property offset The offset of the first resource on this page in the total set of resources
 * @property totalCount The total number of resources
 */
data class Page<out ID: Id, out DATA>(
        val contents: List<Model<ID, DATA>>,
        val offset: Int,
        val totalCount: Int
)