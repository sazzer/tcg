package uk.co.grahamcox.tcg.webapp.acceptance

import org.junit.Before
import org.junit.runner.RunWith
import org.neo4j.driver.v1.AccessMode
import org.neo4j.driver.v1.Driver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import org.springframework.test.context.web.ServletTestExecutionListener
import org.springframework.test.web.client.MockRestServiceServer
import uk.co.grahamcox.tcg.neo4j.executeStatement
import uk.co.grahamcox.tcg.webapp.spring.Application

/**
 * Base class for all Acceptance Tests
 */
@ContextConfiguration(classes = arrayOf(AcceptanceTestConfiguration::class))
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@TestExecutionListeners(
        DirtiesContextTestExecutionListener::class,
        SpringBootDependencyInjectionTestExecutionListener::class,
        DirtiesContextBeforeModesTestExecutionListener::class,
        ServletTestExecutionListener::class
)
open class AcceptanceTestBase {

    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(AcceptanceTestBase::class.java)
    }

    /** The database driver. Note - don't do this */
    @Autowired
    private lateinit var driver: Driver

    /** The Mock Server to use */
    @Autowired
    private lateinit var mockServer: MockRestServiceServer

    /** Reset the Mock Server before each test */
    @Before
    fun resetMockServer() {
        mockServer.reset()
    }

    /**
     * Reset the database before each test
     */
    @Before
    fun cleanDatabase() {
        driver.executeStatement("MATCH (n) OPTIONAL MATCH (n)-[r]-() WITH n,r DELETE n,r", mapOf(), AccessMode.WRITE)
        LOG.debug("Deleted all nodes and relationships")
    }
}