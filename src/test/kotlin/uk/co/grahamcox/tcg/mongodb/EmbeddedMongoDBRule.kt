package uk.co.grahamcox.tcg.neo4j

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.neo4j.driver.v1.Config
import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.GraphDatabase
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.mongodb.EmbeddedMongoDB
import uk.co.grahamcox.tcg.webapp.spring.EmbeddedNeo4j
import java.net.ServerSocket
import java.util.*

/**
 * JUnit Rule to set up an Embedded MongoDB
 */
class EmbeddedMongoDBRule : TestRule {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(EmbeddedMongoDBRule::class.java)
    }

    /** The MongoDB Database */
    lateinit var database: MongoDatabase

    /**
     * Modifies the method-running [Statement] to implement this
     * test-running rule.

     * @param base The [Statement] to be modified
     * *
     * @param description A [Description] of the test implemented in `base`
     * *
     * @return a new statement, which may be the same as `base`,
     * *         a wrapper around `base`, or a completely new Statement.
     */
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            /**
             * Run the action, throwing a `Throwable` if anything goes wrong.
             */
            override fun evaluate() {
                val embeddedMongoDB = EmbeddedMongoDB()

                val mongoClientUri = MongoClientURI(embeddedMongoDB.getUrl())
                try {
                    LOG.debug("Started MongoDB on {}", mongoClientUri)
                    val mongoClient = MongoClient(mongoClientUri)
                    val databaseName = UUID.randomUUID().toString()
                    database = mongoClient.getDatabase(databaseName)
                    base.evaluate()
                } finally {
                    LOG.debug("Stopping MongoDB on {}", mongoClientUri)
                    embeddedMongoDB.shutdown()
                }
            }

        }
    }
}