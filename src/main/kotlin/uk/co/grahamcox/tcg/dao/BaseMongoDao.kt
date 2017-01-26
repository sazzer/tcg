package uk.co.grahamcox.tcg.dao

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import java.time.Clock

/**
 * Base representation of a MongoDB DAO
 * @param ID The type to use for the ID
 * @param DATA The type to use for the model data
 * @property db The Database connection
 * @property collectionName The collection name
 * @property clock The clock
 */
abstract class BaseMongoDao<ID : Id, DATA>(
        private val db: MongoDatabase,
        private val collectionName: String,
        private val clock: Clock
) : BaseDao<ID, DATA>, BaseWritableDao<ID, DATA> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(BaseMongoDao::class.java)
    }
    /** The collection to work with */
    private val collection = db.getCollection(collectionName)
    /**
     * Create a new record with the given data
     * @param data The data to persist
     * @return the persisted record
     */
    override fun create(data: DATA): Model<ID, DATA> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Retrieve a record by it's internal ID
     * @param id The ID of the record
     * @return the record, or null if no record with this internal ID is present
     */
    override fun getById(id: ID): Model<ID, DATA>? {
        val query = BasicDBObject()
                .append("_id", id.id)
        return loadOneWithQuery(query)
    }

    /**
     * Helper to load a single record from the given query
     * @param query The query to execute
     * @return the parsed result
     */
    private fun loadOneWithQuery(query: BasicDBObject): Model<ID, DATA>? {
        LOG.debug("Executing query: {}", query.toJson())
        val model = try {
            val result = collection.find(query).single()
            LOG.trace("Loaded result document: {}", result.toJson())
            parseResult(result)
        } catch (e: NoSuchElementException) {
            null
        }
        LOG.debug("Loaded model: {}", model)
        return model
    }

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    protected abstract fun parseResult(result: Document): Model<ID, DATA>
}