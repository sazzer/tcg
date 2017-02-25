package uk.co.grahamcox.tcg.webapp.cucumber.matcher

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase

/**
 * Matcher to match a database record with the expected values
 * @property db The database connection
 * @property collectionName The collection name
 * @property matcher The matcher to do the real work
 */
class DatabaseMatcher(private val db: MongoDatabase,
                      private val collectionName: String,
                      private val matcher: BeanMatcher) {
    /** The database collection */
    private val collection = db.getCollection(collectionName)

    /**
     * Check that the record with the ID provided is in the database with the correct values
     * @param id The ID to look for
     */
    fun match(id: String, data: Map<String, String>) {
        val document = collection.find(BasicDBObject("_id", id)).single()

        matcher.match(document, data)
    }
}