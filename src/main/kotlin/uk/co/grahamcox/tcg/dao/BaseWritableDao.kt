package uk.co.grahamcox.tcg.dao

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model

/**
 * Base interface from which all DAOs that change data extend
 * @param ID The type to use for the ID
 * @param DATA The type to use for the model data
 */
interface BaseWritableDao<out ID : Id, DATA> {

    /**
     * Create a new record with the given data
     * @param user The data to persist
     * @return the persisted record
     */
    fun create(user: DATA) : Model<ID, DATA>
}