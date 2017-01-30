package uk.co.grahamcox.tcg.mongodb

import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.slf4j.LoggerFactory
import java.time.Clock
import java.util.*

/**
 * Mechanism to seed the database with initial data
 * @property database The database to seed
 * @property clock The clock to use
 * @property seeders The seeders to use
 */
class DatabaseSeeder(private val database: MongoDatabase,
                     private val clock: Clock,
                     private val seeders: Map<String, Seeder>) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(DatabaseSeeder::class.java)
    }

    /**
     * Actually seed the database with the correct values
     */
    fun seed() {
        LOG.info("Seeding initial values into database")
        seeders.mapValues { it.value.generateDefaultData() }
                .onEach { LOG.info("Seeding collection {} With data {}", it.key, it.value) }
                .mapKeys { database.getCollection(it.key) }
                .mapValues { it.value.map { record -> record.toMutableMap() } }
                .mapValues { it.value.map { record ->
                    record.putIfAbsent("_id", UUID.randomUUID().toString())
                    record.putIfAbsent("version", UUID.randomUUID().toString())
                    record.putIfAbsent("created", Date.from(clock.instant()))
                    record.putIfAbsent("updated", Date.from(clock.instant()))
                    record
                } }
                .mapValues { it.value.map(::Document) }
                .forEach { collection, data ->
                    collection.insertMany(data)
                }
    }
}