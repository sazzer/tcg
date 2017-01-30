package uk.co.grahamcox.tcg.webapp.cucumber.seeder

import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*

/**
 * Base class for simple seeders for MongoDB records
 * @property database The database to work with
 * @property collectionName The collection to seed
 * @property fieldMapping The mapping between Cucumber fields and Mongo Fields
 * @property defaultFieldValues The default field values to use
 */
abstract class MongoSeeder(private val database: MongoDatabase,
                           private val collectionName: String,
                           private val fieldMapping: Map<String, String>,
                           private val defaultFieldValues: Map<String, FieldDefaulter>) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(MongoSeeder::class.java)
    }

    /** The collection to seed */
    private val collection = database.getCollection(collectionName)

    /** The more complex mappings, e.g. for nested fields */
    protected open val complexFieldMapping: Map<String, (String, MutableMap<String, Any>) -> Any> = mapOf()
    /** The providers for the default field values for the identity of the record */
    protected val defaultIdentityFieldValues = mapOf(
            "_id" to { UUID.randomUUID().toString() },
            "version" to { UUID.randomUUID().toString() },
            "created" to { Date.from(Instant.now()) },
            "updated" to { Date.from(Instant.now()) }
    )

    /**
     * Actually seed the required value into the database
     * @param details The details to seed into the database
     */
    fun seed(details: Map<String, String>) {
        val params = mutableMapOf<String, Any>()
        defaultIdentityFieldValues.mapValues { it.value.invoke() }
                .forEach { k, v -> params[k] = v }
        defaultFieldValues.mapValues { it.value.getDefault() }
                .forEach { k, v -> params[k] = v }
        details.filterKeys { fieldMapping.containsKey(it) }
                .mapKeys { fieldMapping[it.key]!! }
                .forEach { k, v -> params[k] = v }
        details.filterKeys { complexFieldMapping.containsKey(it) }
                .forEach { k, v ->
                    complexFieldMapping[k]?.invoke(v, params)
                }
        val document = Document(params)

        LOG.debug("Creating record {} in collection {}.{}", document.toJson(), database.name, collectionName)
        collection.insertOne(document)
    }
}