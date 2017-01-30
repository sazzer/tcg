package uk.co.grahamcox.tcg.webapp.cucumber.matcher

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase
import com.winterbe.expekt.should
import org.apache.commons.jxpath.JXPathContext

/**
 * Matcher to match a database record with the expected values
 * @property db The database connection
 * @property collectionName The collection name
 * @property userFieldMapping Mapping of the provided inputs to the fields on the data returned
 */
class DatabaseMatcher(private val db: MongoDatabase,
                      private val collectionName: String,
                      private val userFieldMapping: Map<String, String>) {
    /** The database collection */
    private val collection = db.getCollection(collectionName)

    /**
     * Check that the record with the ID provided is in the database with the correct values
     * @param id The ID to look for
     */
    fun match(id: String, data: Map<String, String>) {
        val document = collection.find(BasicDBObject("_id", id)).single()

        val jxPathContext = JXPathContext.newContext(document)

        data.filterKeys { userFieldMapping.containsKey(it) }
                .mapKeys { userFieldMapping[it.key]!! }
                .forEach { field, value ->
                    jxPathContext.getValue(field).should.equal(value)
                }
    }
}