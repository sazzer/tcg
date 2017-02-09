package uk.co.grahamcox.tcg.abilities.dao

import com.winterbe.expekt.should
import org.bson.Document
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.grahamcox.tcg.abilities.AbilityData
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.mongodb.EmbeddedMongoDBRule
import java.time.*
import java.util.*

/**
 * Unit tests for [AbilitiesDaoMongoImpl]
 */
class AbilitiesDaoMongoImplTest {

    /** The embedded MongoDB */
    @JvmField @Rule
    val mongoRule = EmbeddedMongoDBRule()

    /** the current time */
    private val currentTime = OffsetDateTime.of(2017, 1, 15, 11, 52, 0, 0, ZoneOffset.UTC).toInstant()

    /** The clock to use */
    private val clock = Clock.fixed(currentTime, ZoneId.of("UTC"))

    /** The test subject */
    private lateinit var testSubject: AbilitiesDaoMongoImpl

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = AbilitiesDaoMongoImpl(db = mongoRule.database, clock = clock)
    }
    @Test
    fun `retrieve unknown ability by internal ID`() {
        testSubject.getById(AbilityId("unknown")).should.be.`null`
    }

    @Test
    fun `retrieve known ability by internal ID`() {
        mongoRule.database.getCollection("abilities").insertOne(
                Document()
                        .append("_id", "ECEE75F3-4037-4B1F-891A-C5B06546A0BC")
                        .append("version", "0394E84E-A3F6-4F8D-BA44-3BA845328FCE")
                        .append("created", Date.from(Instant.ofEpochMilli(1483983699000)))
                        .append("updated", Date.from(Instant.ofEpochMilli(1483983699000)))
                        .append("name", "Power Strike")
                        .append("description", "Hit things harder")
        )

        testSubject.getById(AbilityId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC")).should.equal(Model(
                identity = Identity(
                        id = AbilityId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = AbilityData(
                        name = "Power Strike",
                        description = "Hit things harder"
                )
        ))
    }
}