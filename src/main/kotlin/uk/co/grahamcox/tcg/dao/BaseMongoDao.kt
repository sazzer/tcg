package uk.co.grahamcox.tcg.dao

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.*
import java.time.Clock
import java.util.*

/**
 * Base representation of a MongoDB DAO
 * @param ID The type to use for the ID
 * @param DATA The type to use for the model data
 * @param SORT The enum type to use for sort fields
 * @property db The Database connection
 * @property collectionName The collection name
 * @property clock The clock
 */
abstract class BaseMongoDao<ID : Id, DATA, SORT : Enum<SORT>>(
        private val db: MongoDatabase,
        private val collectionName: String,
        private val clock: Clock
) : BaseDao<ID, DATA, SORT>, BaseWritableDao<ID, DATA> {
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
        val document = buildDocument(data)
                .append("_id", UUID.randomUUID().toString())
                .append("version", UUID.randomUUID().toString())
                .append("created", Date.from(clock.instant()))
                .append("updated", Date.from(clock.instant()))
        LOG.debug("Inserting document: {}", document.toJson())
        collection.insertOne(document)
        return parseResult(document)
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
     * Retrieve a list of records
     * @param offset The offset to start listing at
     * @param count The total count to retrieve
     * @param sort The sort to apply
     * @return the page of results
     */
    override fun list(offset: Int, count: Int, sort: List<Sort<SORT>>): Page<ID, DATA> {
        val resultset = collection.find()
        val total = resultset.count()
        val parsedResults = resultset
                .skip(offset)
                .limit(count)
                .toList()
                .map { parseResult(it) }
        return Page(parsedResults, offset, total)
    }

    /**
     * Helper to load a single record from the given query
     * @param query The query to execute
     * @return the parsed result
     */
    protected fun loadOneWithQuery(query: BasicDBObject): Model<ID, DATA>? {
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

    /**
     * Build the document that we will store in the database. Note that this only needs to worry about the data fields
     * as everything else is taken care of automatically
     * @param input The input data
     * @return the document
     */
    protected open fun buildDocument(input: DATA): Document {
        TODO("not implemented")
    }
}