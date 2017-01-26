package uk.co.grahamcox.tcg.stats

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.neo4j.driver.v1.AccessMode
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.neo4j.EmbeddedNeo4jRule
import uk.co.grahamcox.tcg.neo4j.execute
import java.time.*

/**
 * Unit tests for [StatsDaoNeo4jImpl]
 */
class StatsDaoNeo4jImplTest {

    /** The embedded Neo4J */
    @JvmField @Rule
    val neo4jRule = EmbeddedNeo4jRule()

    /** the current time */
    private val currentTime = OffsetDateTime.of(2017, 1, 15, 11, 52, 0, 0, ZoneOffset.UTC).toInstant()

    /** The clock to use */
    private val clock = Clock.fixed(currentTime, ZoneId.of("UTC"))

    /** The test subject */
    private lateinit var testSubject: StatsDaoNeo4jImpl

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = StatsDaoNeo4jImpl(driver = neo4jRule.driver, clock = clock)
    }
    @Test
    fun `retrieve unknown stat by internal ID`() {
        testSubject.getById(StatId("unknown")).should.be.`null`
    }

    @Test
    fun `retrieve known user by internal ID`() {
        neo4jRule.driver.execute(AccessMode.WRITE) { session ->
            session.run("""CREATE (u:Stat {id: "ECEE75F3-4037-4B1F-891A-C5B06546A0BC", version: "0394E84E-A3F6-4F8D-BA44-3BA845328FCE", created: 1483983699000, updated: 1483983699000, name: "Strength", description: "Makes you strong"})""")
        }

        testSubject.getById(StatId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC")).should.equal(Model(
                identity = Identity(
                        id = StatId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = StatData(
                        name = "Strength",
                        description = "Makes you strong"
                )
        ))
    }
}