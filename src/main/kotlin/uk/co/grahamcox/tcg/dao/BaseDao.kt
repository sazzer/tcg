package uk.co.grahamcox.tcg.dao

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model

/**
 * Base interface from which all DAOs that read data extend
 * @param ID The type to use for the ID
 * @param DATA The type to use for the model data
 */
interface BaseDao<ID : Id, out DATA> {
    /**
     * Retrieve a record by it's internal ID
     * @param id The ID of the record
     * @return the record, or null if no record with this internal ID is present
     */
    fun getById(id: ID) : Model<ID, DATA>?
}