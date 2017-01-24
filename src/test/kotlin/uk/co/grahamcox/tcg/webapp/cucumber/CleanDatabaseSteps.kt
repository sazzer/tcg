package uk.co.grahamcox.tcg.webapp.cucumber

import cucumber.api.java.Before
import org.neo4j.driver.v1.AccessMode
import org.neo4j.driver.v1.Driver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import uk.co.grahamcox.tcg.neo4j.executeStatement
import uk.co.grahamcox.tcg.webapp.cucumber.spring.CucumberTestConfiguration
import uk.co.grahamcox.tcg.webapp.spring.Application

/**
 * Steps to clean the database before the test runs
 */
@ContextConfiguration(classes = arrayOf(CucumberTestConfiguration::class))
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CleanDatabaseSteps {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(CleanDatabaseSteps::class.java)
    }

    /** The Neo4j Driver */
    @Autowired
    private lateinit var driver: Driver

    /**
     * Clean the database
     */
    @Before
    fun cleanDatabase() {
        driver.executeStatement("MATCH (n) OPTIONAL MATCH (n)-[r]-() WITH n,r DELETE n,r", mapOf(), AccessMode.WRITE)
        LOG.debug("Deleted all nodes and relationships")
    }
}