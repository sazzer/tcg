package uk.co.grahamcox.tcg.user.dao

import com.winterbe.expekt.should
import org.bson.Document
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.mongodb.EmbeddedMongoDBRule
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import java.time.*
import java.util.*

/**
 * Unit tests for [UserDaoMongoImpl]
 */
class UserDaoMongoImplTest {
    /** The embedded MongoDB */
    @JvmField @Rule
    val mongoRule = EmbeddedMongoDBRule()

    /** the current time */
    private val currentTime = OffsetDateTime.of(2017, 1, 15, 11, 52, 0, 0, ZoneOffset.UTC).toInstant()

    /** The clock to use */
    private val clock = Clock.fixed(currentTime, ZoneId.of("UTC"))

    /** The test subject */
    private lateinit var testSubject: UserDaoMongoImpl

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = UserDaoMongoImpl(db = mongoRule.database, clock = clock)
    }
    @Test
    fun `retrieve unknown user by internal ID`() {
        testSubject.getById(UserId("unknown")).should.be.`null`
    }


    @Test
    fun `retrieve known user by internal ID`() {
        mongoRule.database.getCollection("users").insertOne(
                Document(mapOf(
                        "_id" to "ECEE75F3-4037-4B1F-891A-C5B06546A0BC",
                        "version" to "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        "created" to Date.from(Instant.ofEpochMilli(1483983699000)),
                        "updated" to Date.from(Instant.ofEpochMilli(1483983699000)),
                        "name" to "Graham",
                        "email" to "graham@grahamcox.co.uk",
                        "providers" to mapOf(
                                "google" to "googleId",
                                "twitter" to "@twitterId"
                        )
                ))
        )

        testSubject.getById(UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC")).should.equal(Model(
                identity = Identity(
                        id = UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = UserData(
                        name = "Graham",
                        email = "graham@grahamcox.co.uk",
                        providers = mapOf(
                                "google" to "googleId",
                                "twitter" to "@twitterId"
                        )
                )
        ))
    }

    @Test
    fun `retrieve empty user by internal ID`() {
        mongoRule.database.getCollection("users").insertOne(
                Document(mapOf(
                        "_id" to "ECEE75F3-4037-4B1F-891A-C5B06546A0BC",
                        "version" to "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        "created" to Date.from(Instant.ofEpochMilli(1483983699000)),
                        "updated" to Date.from(Instant.ofEpochMilli(1483983699000)),
                        "name" to "Graham"
                ))
        )

        testSubject.getById(UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC")).should.equal(Model(
                identity = Identity(
                        id = UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = UserData(
                        name = "Graham",
                        email = null,
                        providers = mapOf()
                )
        ))
    }

    @Test
    fun `create fully populated user`() {
        val user = testSubject.create(UserData(
                name = "Graham",
                email = "graham@grahamcox.co.uk",
                providers = mapOf(
                        "google" to "googleId",
                        "twitter" to "@twitterId"
                )
        ))
        val reretrieve = testSubject.getById(user.identity.id)
        reretrieve.should.equal(user)
    }

    @Test
    fun `create sparsely populated user`() {
        val user = testSubject.create(UserData(
                name = "Graham",
                email = null,
                providers = mapOf()
        ))
        val reretrieve = testSubject.getById(user.identity.id)
        reretrieve.should.equal(user)
    }

}