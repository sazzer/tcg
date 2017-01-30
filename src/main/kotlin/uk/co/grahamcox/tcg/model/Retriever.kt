package uk.co.grahamcox.tcg.model

/**
 * Base interface for retrieving data
 */
interface Retriever<ID : Id, out DATA, FILTER : Enum<FILTER>, SORT : Enum<SORT>> {
    /**
     * Retrieve a record by it's internal ID
     * @param id The ID of the record
     * @return the record
     * @throws UnknownResourceException if the record doesn't exist
     */
    fun retrieveById(id: ID) : Model<ID, DATA>

    /**
     * Retrieve a list of records
     * @param offset The offset to start listing at
     * @param count The total count to retrieve
     * @param filter Any filters to apply
     * @param sort The sort to apply
     * @return the page of results
     */
    fun list(offset: Int,
             count: Int,
             filter: Map<FILTER, Any>,
             sort: List<Sort<SORT>>): Page<ID, DATA>
}