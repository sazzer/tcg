package uk.co.grahamcox.tcg.dao

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.litote.kmongo.*
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.*
import java.time.Clock
import java.util.*

/**
 * Base representation of a MongoDB DAO using KMongo
 * @param ID The type to use for the ID
 * @param DATA The type to use for the model data
 * @param MODEL The database model type to use for the model data
 * @param FILTER The enum type to use for filter fields
 * @param SORT The enum type to use for sort fields
 * @property db The Database connection
 * @property collectionName The collection name
 * @property clock The clock
 * @param modelType The type to use for the database model
 */
abstract class BaseKMongoDao<ID : Id, DATA, in MODEL, FILTER : Enum<FILTER>, SORT : Enum<SORT>>(
        private val db: MongoDatabase,
        private val collectionName: String,
        private val clock: Clock,
        modelType: Class<MODEL>
) : BaseDao<ID, DATA, FILTER, SORT>, BaseWritableDao<ID, DATA> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(BaseKMongoDao::class.java)
    }



    /** The raw collection to use */
    private val collection = db.getCollection<MODEL>(collectionName, modelType)
    /**
     * Create a new record with the given data
     * @param data The data to persist
     * @return the persisted record
     */
    override fun create(data: DATA): Model<ID, DATA> {
        TODO("Not implemented yet")
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
     * @param filter Any filters to apply
     * @param sort The sort to apply
     * @return the page of results
     */
    override fun list(offset: Int,
                      count: Int,
                      filter: Map<FILTER, Any>,
                      sort: List<Sort<SORT>>): Page<ID, DATA> {

        val sortFields = sort.filter { sortFields.containsKey(it.sort) }
                .map { sortFields[it.sort]!! to it.order }
                .map { it.first to when (it.second) {
                    SortOrder.ASCENDING -> 1
                    SortOrder.DESCENDING -> -1
                }}
                .toMap()

        val filterFields = filter.filterKeys { filterFields.containsKey(it) }
                .mapKeys { filterFields[it.key]!! }

        LOG.debug("Retrieving records from collection {} with sort {} and filter {}",
                collectionName, sort, filter)
        val resultset = collection.find(Document(filterFields))

        val total = resultset.count()
        val parsedResults = resultset
                .sort(BasicDBObject(sortFields))
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
        val result = collection.findOne(query)

        val model = result?.let {
            LOG.trace("Loaded result document: {}", it)
            parseResult(it)
        }

        LOG.debug("Loaded model: {}", model)
        return model
    }

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    protected abstract fun parseResult(result: MODEL): Model<ID, DATA>

    /**
     * The mapping of sort fields to the actual database field
     */
    protected open val sortFields: Map<SORT, String>
        get() { TODO("not implemented") }

    /**
     * The mapping of filter fields to the actual database field
     */
    protected open val filterFields: Map<FILTER, String>
        get() { TODO("not implemented") }
}