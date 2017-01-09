package uk.co.grahamcox.tcg.neo4j

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.neo4j.driver.v1.Config
import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.GraphDatabase
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.webapp.spring.EmbeddedNeo4j
import java.net.ServerSocket

/**
 * JUnit Rule to set up an Embedded Neo4j
 */
class EmbeddedNeo4jRule : TestRule {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(EmbeddedNeo4jRule::class.java)
    }

    /** The database driver config */
    private val config = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE)

    /** The Neo4j Driver */
    lateinit var driver: Driver

    /** The shell port to use */
    val shellPort = 5555

    /** The bolt port to use */
    val boltPort = 7687

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

                LOG.debug("Starting Neo4J. Bolt={}, shell={}", boltPort, shellPort)
                val embeddedNeo4j = EmbeddedNeo4j(shellPort, boltPort)

                try {
                    embeddedNeo4j.afterPropertiesSet()
                    driver = GraphDatabase.driver("bolt://localhost:${boltPort}", config.toConfig())
                    base.evaluate()
                } finally {
                    LOG.debug("Stopping Neo4J. Bolt={}, shell={}", boltPort, shellPort)
                    embeddedNeo4j.destroy()
                }
            }

        }
    }
}