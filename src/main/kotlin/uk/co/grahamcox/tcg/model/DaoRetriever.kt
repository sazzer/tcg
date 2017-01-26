package uk.co.grahamcox.tcg.model

import uk.co.grahamcox.tcg.dao.BaseDao

/**
 * Standard implementation of the Retriever that works in terms of a [BaseDao]
 * @property dao The DAO to work with
 */
open class DaoRetriever<ID : Id, out DATA>(private val dao: BaseDao<ID, DATA>) : Retriever<ID, DATA> {
    /**
     * Retrieve a record by it's internal ID
     * @param id The ID of the record
     * @return the record
     * @throws UnknownResourceException if the record doesn't exist
     */
    override fun retrieveById(id: ID) = dao.getById(id) ?: throw UnknownResourceException(id)
}