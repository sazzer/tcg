package uk.co.grahamcox.tcg.neo4j

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.springframework.boot.actuate.health.Status

/**
 * Unit Tests for the MongoDB Healthcheck
 */
class MongoHealthcheckTest {
    /** The embedded MongoDB */
    @JvmField @Rule
    val mongoDBRule = EmbeddedMongoDBRule()

    /** The test subject */
    private lateinit var testSubject: MongoHealthcheck

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = MongoHealthcheck(mongoDBRule.database)
    }

    @Test
    fun `database is up`() {
        testSubject.health().status.should.equal(Status.UP)
    }

    @Test
    fun `healthcheck returns collections on empty database`() {
        testSubject.health().details["collectionNames"].should.equal(listOf<String>())
        testSubject.health().details["collectionCount"].should.equal(0)
    }

    @Test
    fun `healthcheck returns collections on populated database`() {
        mongoDBRule.database.createCollection("abc")
        mongoDBRule.database.createCollection("def")

        testSubject.health().details["collectionNames"].should.equal(listOf<String>("abc", "def"))
        testSubject.health().details["collectionCount"].should.equal(2)
    }
}