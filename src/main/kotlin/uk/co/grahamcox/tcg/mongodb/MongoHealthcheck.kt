package uk.co.grahamcox.tcg.mongodb

import com.mongodb.client.MongoDatabase
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health

/**
 * Ensure that the MongoDB Database is healthy
 * @property db The connection to the Mongo Database
 */
class MongoHealthcheck(private val db: MongoDatabase) : AbstractHealthIndicator() {
    /**
     * Actually perform the healthcheck
     * @param builder The builder to report health status and details to
     */
    override fun doHealthCheck(builder: Health.Builder) {
        val collectionNames = db.listCollectionNames().toList().sorted()
        builder.up()
                .withDetail("collectionNames", collectionNames)
                .withDetail("collectionCount", collectionNames.size)
    }
}