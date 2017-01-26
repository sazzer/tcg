package uk.co.grahamcox.tcg.dao

import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.StatementResult
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.neo4j.executeStatement
import java.time.Clock
import java.util.*

/**
 * Default implementation of the [BaseDao] and [BaseWritableDao] interfaces, working against Neo4J
 */
abstract class BaseNeo4jDao<ID : Id, DATA>(
        private val driver: Driver,
        private val clock: Clock
) : BaseDao<ID, DATA>, BaseWritableDao<ID, DATA> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(BaseDao::class.java)
    }
    /** Query to use to get a single record by ID */
    protected abstract val getByIdQuery: String

    /** Query to use to create a new record */
    protected open val createQuery: String
        get() = TODO("not implemented")

    /**
     * Create a new record with the given data
     * @param data The data to persist
     * @return the persisted record
     */
    override fun create(data: DATA): Model<ID, DATA> {
        LOG.debug("Creating record with data {}", data)

        return loadWithQuery(
                createQuery,
                buildCreateIdentityParameters() + buildCreateParameters(data)
        ) ?: throw IllegalStateException("Failed to create record")
    }

    /**
     * Retrieve a record by it's internal ID
     * @param id The ID of the record
     * @return the record, or null if no record with this internal ID is present
     */
    override fun getById(id: ID): Model<ID, DATA>? =
            loadWithQuery(getByIdQuery, mapOf(
                    "id" to id.id
            ))

    /**
     * Helper to load the record returned by the given query
     * @param query The query to execute
     * @param parameters The parameters to the query
     * @return the record, if any was found
     */
    protected fun loadWithQuery(query: String, parameters: Map<String, Any?>): Model<ID, DATA>? {
        val result = driver.executeStatement(query, parameters)
        val user = when (result.hasNext()) {
            true -> parseResult(result)
            false -> null
        }
        LOG.debug("Found record: {}", user)

        return user
    }

    /**
     * Helper to build the parameters required to create a new node. These are the inputs to the [createQuery]
     * @param data The data to build the parameters from
     * @return the parameters
     */
    protected open fun buildCreateParameters(data: DATA): Map<String, *> {
        TODO("not implemented")
    }

    /**
     * Helper to build the default set of parameters needed for identifying a record
     * @return the parameters to represent the identity of a new record
     */
    protected fun buildCreateIdentityParameters(): Map<String, *> = mapOf(
            "id" to UUID.randomUUID().toString(),
            "version" to UUID.randomUUID().toString(),
            "created" to clock.instant().toEpochMilli(),
            "updated" to clock.instant().toEpochMilli()
    )

    /**
     * Parse the given statement result into a single record
     * Note that the statement result has to be provided because there might be multiple rows returned representing a
     * single record
     * @param result the statement result to parse
     * @return the model parsed from the result
     */
    protected abstract fun parseResult(result: StatementResult): Model<ID, DATA>
}