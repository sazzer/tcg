package uk.co.grahamcox.tcg.neo4j

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.neo4j.driver.internal.InternalRecord
import org.neo4j.driver.internal.value.IntegerValue
import org.neo4j.driver.v1.*
import org.springframework.boot.actuate.health.Status

/**
 * Unit Tests for the Neo4j Healthcheck
 */
@RunWith(MockitoJUnitRunner::class)
class Neo4jHealthcheckTest {
    /** The embedded Neo4J */
    @JvmField @Rule
    val neo4jRule = EmbeddedNeo4jRule()

    /** The test subject */
    private lateinit var testSubject: Neo4jHealthcheck

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = Neo4jHealthcheck(neo4jRule.driver)
    }

    @Test
    fun `database is up`() {
        testSubject.health().status.should.equal(Status.UP)
    }

    @Test
    fun `healthcheck returns number of nodes on empty database`() {
        testSubject.health().details["nodeCount"].should.equal(0)
    }

    @Test
    fun `healthcheck returns number of nodes on populatd database`() {
        neo4jRule.driver.executeStatement("CREATE (n:ArbitraryNode)")

        testSubject.health().details["nodeCount"].should.equal(1)
    }
}