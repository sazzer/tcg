package uk.co.grahamcox.tcg.webapp.acceptance

import com.jayway.jsonpath.JsonPath
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.neo4j.driver.v1.AccessMode
import org.neo4j.driver.v1.Driver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import org.springframework.test.context.web.ServletTestExecutionListener
import uk.co.grahamcox.tcg.neo4j.executeStatement
import uk.co.grahamcox.tcg.webapp.acceptance.AcceptanceTestConfiguration
import uk.co.grahamcox.tcg.webapp.acceptance.Requester
import uk.co.grahamcox.tcg.webapp.spring.Application
import java.util.*

/**
 * Acceptance Test for the Healthchecks
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
class HealthcheckIT {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(HealthcheckIT::class.java)
    }

    /** The requester to use */
    @Autowired
    private lateinit var requester: Requester

    /** The database driver. Note - don't do this */
    @Autowired
    private lateinit var driver: Driver

    @Before
    fun cleanDatabase() {
        driver.executeStatement("MATCH (n) OPTIONAL MATCH (n)-[r]-() WITH n,r DELETE n,r", mapOf(), AccessMode.WRITE)
        LOG.debug("Deleted all nodes and relationships")
    }

    @Test
    fun `Checking the healthchecks with an empty database`() {
        requester.get("/health").let { response ->
            response.statusCode.should.equal(HttpStatus.OK)
            JsonPath.read<String>(response.body, "$.status").should.equal("UP")
            JsonPath.read<Int>(response.body, "$.neo4jHealthchecks.nodeCount").should.equal(0)
        }
    }

    @Test
    fun `Checking the healthchecks with nodes in the database`() {
        driver.executeStatement("CREATE (n:ArbitraryNode {id: {id}})",
                mapOf("id" to UUID.randomUUID().toString()),
                AccessMode.WRITE)

        requester.get("/health").let { response ->
            response.statusCode.should.equal(HttpStatus.OK)
            JsonPath.read<String>(response.body, "$.status").should.equal("UP")
            JsonPath.read<Int>(response.body, "$.neo4jHealthchecks.nodeCount").should.equal(1)
        }
    }
}