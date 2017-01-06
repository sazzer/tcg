package uk.co.grahamcox.tcg.neo4j

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.winterbe.expekt.should
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
    /** The Neo4j Driver */
    @Mock
    private lateinit var driver: Driver

    /** The test subject */
    @InjectMocks
    private lateinit var testSubject: Neo4jHealthcheck

    @Test
    fun `database is up`() {
        val result = mock<StatementResult> {
            on { this.single() } doReturn InternalRecord(listOf("totalCount"), arrayOf(IntegerValue(0)))
        }

        val session = mock<Session> {
            on { this.run("MATCH (n) RETURN COUNT(n) AS totalCount", kotlin.collections.mapOf()) } doReturn result
        }

        whenever(driver.session(AccessMode.READ)).thenReturn(session)
        testSubject.health().status.should.equal(Status.UP)
    }

    @Test
    fun `database is down`() {
        val session = mock<Session> {
            on { this.run("MATCH (n) RETURN COUNT(n) AS totalCount", kotlin.collections.mapOf()) } doThrow IllegalStateException()
        }

        whenever(driver.session(AccessMode.READ)).thenReturn(session)
        testSubject.health().status.should.equal(Status.DOWN)
    }


    @Test
    fun `healthcheck returns number of nodes`() {
        val result = mock<StatementResult> {
            on { this.single() } doReturn InternalRecord(listOf("totalCount"), arrayOf(IntegerValue(123)))
        }

        val session = mock<Session> {
            on { this.run("MATCH (n) RETURN COUNT(n) AS totalCount", kotlin.collections.mapOf()) } doReturn result
        }

        whenever(driver.session(AccessMode.READ)).thenReturn(session)
        testSubject.health().details["nodeCount"].should.equal(123)
    }
}