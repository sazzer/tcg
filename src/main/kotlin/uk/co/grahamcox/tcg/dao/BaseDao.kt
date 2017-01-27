package uk.co.grahamcox.tcg.dao

import uk.co.grahamcox.tcg.model.*

/**
 * Base interface from which all DAOs that read data extend
 * @param ID The type to use for the ID
 * @param DATA The type to use for the model data
 * @param SORT The enum type to use for sort fields
 */
interface BaseDao<ID : Id, out DATA, SORT : Enum<SORT>> {
    /**
     * Retrieve a record by it's internal ID
     * @param id The ID of the record
     * @return the record, or null if no record with this internal ID is present
     */
    fun getById(id: ID) : Model<ID, DATA>?

    /**
     * Retrieve a list of records
     * @param offset The offset to start listing at
     * @param count The total count to retrieve
     * @param sort The sort to apply
     * @return the page of results
     */
    fun list(offset: Int, count: Int, sort: List<Sort<SORT>>): Page<ID, DATA>
}