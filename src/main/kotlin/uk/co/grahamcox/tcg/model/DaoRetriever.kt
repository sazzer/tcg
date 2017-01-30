package uk.co.grahamcox.tcg.model

import uk.co.grahamcox.tcg.dao.BaseDao

/**
 * Standard implementation of the Retriever that works in terms of a [BaseDao]
 * @property dao The DAO to work with
 */
open class DaoRetriever<ID : Id, out DATA, FILTER : Enum<FILTER>, SORT : Enum<SORT>>(
        private val dao: BaseDao<ID, DATA, FILTER, SORT>) : Retriever<ID, DATA, FILTER, SORT> {
    /**
     * Retrieve a record by it's internal ID
     * @param id The ID of the record
     * @return the record
     * @throws UnknownResourceException if the record doesn't exist
     */
    override fun retrieveById(id: ID) = dao.getById(id) ?: throw UnknownResourceException(id)

    /**
     * Retrieve a list of records
     * @param offset The offset to start listing at
     * @param count The total count to retrieve
     * @param sort The sort to apply
     * @return the page of results
     */
    override fun list(offset: Int, count: Int, sort: List<Sort<SORT>>) =
            dao.list(offset, count, sort)
}