package uk.co.grahamcox.tcg.genders

import com.winterbe.expekt.should
import org.bson.Document
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.mongodb.EmbeddedMongoDBRule
import uk.co.grahamcox.tcg.races.RaceId
import java.time.*
import java.util.*

/**
 * Unit tests for [GendersDaoMongoImpl]
 */
class GendersDaoMongoImplTest {

    /** The embedded MongoDB */
    @JvmField @Rule
    val mongoRule = EmbeddedMongoDBRule()

    /** the current time */
    private val currentTime = OffsetDateTime.of(2017, 1, 15, 11, 52, 0, 0, ZoneOffset.UTC).toInstant()

    /** The clock to use */
    private val clock = Clock.fixed(currentTime, ZoneId.of("UTC"))

    /** The test subject */
    private lateinit var testSubject: GendersDaoMongoImpl

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = GendersDaoMongoImpl(db = mongoRule.database, clock = clock)
    }
    @Test
    fun `retrieve unknown gender by internal ID`() {
        testSubject.getById(GenderId("unknown")).should.be.`null`
    }

    @Test
    fun `retrieve known gender by internal ID`() {
        mongoRule.database.getCollection("genders").insertOne(
                Document()
                        .append("_id", "ECEE75F3-4037-4B1F-891A-C5B06546A0BC")
                        .append("version", "0394E84E-A3F6-4F8D-BA44-3BA845328FCE")
                        .append("created", Date.from(Instant.ofEpochMilli(1483983699000)))
                        .append("updated", Date.from(Instant.ofEpochMilli(1483983699000)))
                        .append("name", "Male")
                        .append("description", "Man")
                        .append("race", "human")
        )

        testSubject.getById(GenderId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC")).should.equal(Model(
                identity = Identity(
                        id = GenderId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = GenderData(
                        name = "Male",
                        description = "Man",
                        race = RaceId("human")
                )
        ))
    }
}