package uk.co.grahamcox.tcg.characters.dao

import com.winterbe.expekt.should
import org.bson.Document
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.characters.CharacterData
import uk.co.grahamcox.tcg.characters.CharacterId
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.mongodb.EmbeddedMongoDBRule
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.skills.SkillId
import uk.co.grahamcox.tcg.user.UserId
import java.time.*
import java.util.*

/**
 * Unit tests for the [CharacterDaoMongoImpl]
 */
class CharacterDaoMongoImplTest {
    /** The embedded MongoDB */
    @JvmField @Rule
    val mongoRule = EmbeddedMongoDBRule()

    /** the current time */
    private val currentTime = OffsetDateTime.of(2017, 1, 15, 11, 52, 0, 0, ZoneOffset.UTC).toInstant()

    /** The clock to use */
    private val clock = Clock.fixed(currentTime, ZoneId.of("UTC"))

    /** The test subject */
    private lateinit var testSubject: CharacterDaoMongoImpl

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = CharacterDaoMongoImpl(db = mongoRule.database, clock = clock)
    }

    @Test
    fun `retrieve unknown character by ID`() {
        testSubject.getById(CharacterId("unknown")).should.be.`null`
    }

    @Test
    fun `retrieve known character by ID`() {
        mongoRule.database.getCollection("characters").insertOne(
                Document(mapOf(
                        "_id" to "ECEE75F3-4037-4B1F-891A-C5B06546A0BC",
                        "version" to "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        "created" to Date.from(Instant.ofEpochMilli(1483983699000)),
                        "updated" to Date.from(Instant.ofEpochMilli(1483983699000)),
                        "name" to "Graham",
                        "owner" to "graham",
                        "race" to "human",
                        "gender" to "male",
                        "class" to "warrior",
                        "attributes" to mapOf<String, Long>(),
                        "skills" to mapOf<String, Long>(),
                        "abilities" to listOf<String>()
                ))
        )


        testSubject.getById(CharacterId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC")).should.equal(Model(
                identity = Identity(
                        id = CharacterId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = CharacterData(
                        name = "Graham",
                        owner = UserId("graham"),
                        race = RaceId("human"),
                        gender = GenderId("male"),
                        characterClass = ClassId("warrior"),
                        attributes = mapOf(),
                        skills = mapOf(),
                        abilities = setOf()
                )
        ))
    }

    @Test
    fun `retrieve fully populated known character by ID`() {
        mongoRule.database.getCollection("characters").insertOne(
                Document(mapOf(
                        "_id" to "ECEE75F3-4037-4B1F-891A-C5B06546A0BC",
                        "version" to "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        "created" to Date.from(Instant.ofEpochMilli(1483983699000)),
                        "updated" to Date.from(Instant.ofEpochMilli(1483983699000)),
                        "name" to "Graham",
                        "owner" to "graham",
                        "race" to "human",
                        "gender" to "male",
                        "class" to "warrior",
                        "attributes" to mapOf(
                                "str" to 10L,
                                "wis" to 5L
                        ),
                        "skills" to mapOf(
                                "swords" to 5L,
                                "clubs" to 10L
                        ),
                        "abilities" to listOf(
                                "concentration",
                                "powerstrike"
                        )
                ))
        )


        testSubject.getById(CharacterId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC")).should.equal(Model(
                identity = Identity(
                        id = CharacterId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = CharacterData(
                        name = "Graham",
                        owner = UserId("graham"),
                        race = RaceId("human"),
                        gender = GenderId("male"),
                        characterClass = ClassId("warrior"),
                        attributes = mapOf(
                                AttributeId("str") to 10L,
                                AttributeId("wis") to 5L
                        ),
                        skills = mapOf(
                                SkillId("swords") to 5L,
                                SkillId("clubs") to 10L
                        ),
                        abilities = setOf(
                                AbilityId("concentration"),
                                AbilityId("powerstrike")
                        )
                )
        ))
    }

    @Test
    fun `create character`() {
        val input = CharacterData(
                name = "Graham",
                owner = UserId("graham"),
                race = RaceId("human"),
                gender = GenderId("male"),
                characterClass = ClassId("warrior"),
                attributes = mapOf(
                        AttributeId("str") to 10L,
                        AttributeId("wis") to 5L
                ),
                skills = mapOf(
                        SkillId("swords") to 5L,
                        SkillId("clubs") to 10L
                ),
                abilities = setOf(
                        AbilityId("concentration"),
                        AbilityId("powerstrike")
                )
        )

        val created = testSubject.create(input)
        created.data.should.equal(input)

        val reretrieved = testSubject.getById(created.identity.id)
        reretrieved.should.equal(created)
    }
}