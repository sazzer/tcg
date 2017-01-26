package uk.co.grahamcox.tcg.model

/**
 * Base interface for retrieving data
 */
interface Retriever<ID : Id, out DATA> {
    /**
     * Retrieve a record by it's internal ID
     * @param id The ID of the record
     * @return the record
     * @throws UnknownResourceException if the record doesn't exist
     */
    fun retrieveById(id: ID) : Model<ID, DATA>

}