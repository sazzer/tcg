package uk.co.grahamcox.tcg.characters

import com.nhaarman.mockito_kotlin.*
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.attributes.AttributeSort
import uk.co.grahamcox.tcg.characters.dao.CharacterDao
import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.classes.ClassSort
import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.genders.GenderSort
import uk.co.grahamcox.tcg.model.*
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.races.RaceSort
import uk.co.grahamcox.tcg.skills.SkillData
import uk.co.grahamcox.tcg.skills.SkillId
import uk.co.grahamcox.tcg.skills.SkillSort
import uk.co.grahamcox.tcg.user.UserId
import java.time.Instant
import java.util.*

/**
 * Unit tests for [CharacterCreatorImpl]
 */
class CharacterCreatorImplTest {
    /** Mock Race Retriever */
    private val raceRetriever: Retriever<RaceId, RaceData, NoFilter, RaceSort> = mock {
        on { this.retrieveById(RaceId("unknown")) } doThrow  UnknownResourceException(RaceId("unknown"))
        on { this.retrieveById(RaceId("human")) } doReturn buildRace("human")
        on { this.retrieveById(RaceId("dwarf")) } doReturn buildRace(id = "dwarf",
                attributes = mapOf("str" to 5L),
                skills = mapOf("swords" to 10L),
                abilities = setOf("powerstrike"))
    }

    /** Mock Gender Retriever */
    private val genderRetriever: Retriever<GenderId, GenderData, NoFilter, GenderSort> = mock {
        on { this.retrieveById(GenderId("unknown")) } doThrow UnknownResourceException(GenderId("unknown"))
        on { this.retrieveById(GenderId("humanmale")) } doReturn buildGender("humanmale", "human")
        on { this.retrieveById(GenderId("dwarfmale")) } doReturn buildGender(id = "dwarfmale",
                race = "dwarf",
                attributes = mapOf("str" to 5L),
                skills = mapOf("swords" to 10L),
                abilities = setOf("powerstrike"))
    }

    /** Mock Class Retriever */
    private val classesRetriever: Retriever<ClassId, ClassData, NoFilter, ClassSort> = mock {
        on { this.retrieveById(ClassId("unknown")) } doThrow UnknownResourceException(ClassId("unknown"))
        on { this.retrieveById(ClassId("warrior")) } doReturn buildClass("warrior")
        on { this.retrieveById(ClassId("mage")) } doReturn buildClass(id = "mage",
                attributes = mapOf("wis" to 5L),
                skills = mapOf("spells" to 10L),
                abilities = setOf("concentration"))
    }

    /** Mock Attribute Retriever */
    private val attributeRetriever: Retriever<AttributeId, AttributeData, NoFilter, AttributeSort> = mock {
        on { this.list(0, Int.MAX_VALUE, mapOf(), listOf()) } doReturn Page(
                listOf(
                        buildAttribute("str", 10),
                        buildAttribute("wis", 15)
                ), 0, 0
        )
    }

    /** Mock Skill Retriever */
    private val skillRetriever: Retriever<SkillId, SkillData, NoFilter, SkillSort> = mock {
        on { this.list(0, Int.MAX_VALUE, mapOf(), listOf()) } doReturn Page(
                listOf(
                        buildSkill("swords", 10),
                        buildSkill("spells", 15)
                ), 0, 0
        )
    }

    /** Mock Character DAO */
    private val characterDao: CharacterDao = mock {  }

    /** The test subject */
    private val testSubject = CharacterCreatorImpl(
            raceRetriever,
            genderRetriever,
            classesRetriever,
            attributeRetriever,
            skillRetriever,
            characterDao)

    /** Set up the DAO to react correctly */
    @Before
    fun setupDao() {
        val now = Instant.now()
        whenever(characterDao.create(any())).thenAnswer {
            Model(
                    identity = Identity(
                            id = CharacterId("characterId"),
                            version = "version",
                            created = now,
                            updated = now
                    ),
                    data = it
            )
        }
    }

    @Test(expected = UnknownRaceException::class)
    fun `unknown race`() {
        testSubject.createCharacter(CharacterTemplate(
                owner = UserId("graham"),
                name = "Bob",
                race = RaceId("unknown"),
                gender = GenderId("humanmale"),
                characterClass = ClassId("warrior")
        ))
    }

    @Test(expected = UnknownGenderException::class)
    fun `unknown gender`() {
        testSubject.createCharacter(CharacterTemplate(
                owner = UserId("graham"),
                name = "Bob",
                race = RaceId("human"),
                gender = GenderId("unknown"),
                characterClass = ClassId("warrior")
        ))
    }

    @Test(expected = UnknownClassException::class)
    fun `unknown class`() {
        testSubject.createCharacter(CharacterTemplate(
                owner = UserId("graham"),
                name = "Bob",
                race = RaceId("human"),
                gender = GenderId("humanmale"),
                characterClass = ClassId("unknown")
        ))
    }

    @Test(expected = UnknownGenderException::class)
    fun `wrong gender for race`() {
        testSubject.createCharacter(CharacterTemplate(
                owner = UserId("graham"),
                name = "Bob",
                race = RaceId("human"),
                gender = GenderId("dwarfmale"),
                characterClass = ClassId("warrior")
        ))
    }

    @Test
    fun `success with no bonuses`() {
        testSubject.createCharacter(CharacterTemplate(
                owner = UserId("graham"),
                name = "Bob",
                race = RaceId("human"),
                gender = GenderId("humanmale"),
                characterClass = ClassId("warrior")
        ))

        argumentCaptor<CharacterData>().apply {
            verify(characterDao).create(capture())
            allValues.should.has.size(1)

            firstValue.apply {
                owner.should.equal(UserId("graham"))
                name.should.equal("Bob")
                race.should.equal(RaceId("human"))
                gender.should.equal(GenderId("humanmale"))
                characterClass.should.equal(ClassId("warrior"))

                attributes.should.have.size(2)
                attributes.should.contain(AttributeId("str") to 10L)
                attributes.should.contain(AttributeId("wis") to 15L)

                skills.should.have.size(2)
                skills.should.contain(SkillId("swords") to 10L)
                skills.should.contain(SkillId("spells") to 15L)

                abilities.should.be.empty
            }
        }
    }

    @Test
    fun `success with bonuses from class`() {
        testSubject.createCharacter(CharacterTemplate(
                owner = UserId("graham"),
                name = "Bob",
                race = RaceId("human"),
                gender = GenderId("humanmale"),
                characterClass = ClassId("mage")
        ))

        argumentCaptor<CharacterData>().apply {
            verify(characterDao).create(capture())
            allValues.should.has.size(1)

            firstValue.apply {
                owner.should.equal(UserId("graham"))
                name.should.equal("Bob")
                race.should.equal(RaceId("human"))
                gender.should.equal(GenderId("humanmale"))
                characterClass.should.equal(ClassId("mage"))

                attributes.should.have.size(2)
                attributes.should.contain(AttributeId("str") to 10L)
                attributes.should.contain(AttributeId("wis") to 20L)

                skills.should.have.size(2)
                skills.should.contain(SkillId("swords") to 10L)
                skills.should.contain(SkillId("spells") to 25L)

                abilities.should.have.size(1)
                abilities.should.contain(AbilityId("concentration"))
            }
        }
    }

    @Test
    fun `success with bonuses from everywhere`() {
        testSubject.createCharacter(CharacterTemplate(
                owner = UserId("graham"),
                name = "Bob",
                race = RaceId("dwarf"),
                gender = GenderId("dwarfmale"),
                characterClass = ClassId("mage")
        ))

        argumentCaptor<CharacterData>().apply {
            verify(characterDao).create(capture())
            allValues.should.has.size(1)

            firstValue.apply {
                owner.should.equal(UserId("graham"))
                name.should.equal("Bob")
                race.should.equal(RaceId("dwarf"))
                gender.should.equal(GenderId("dwarfmale"))
                characterClass.should.equal(ClassId("mage"))

                attributes.should.have.size(2)
                attributes.should.contain(AttributeId("str") to 20L)
                attributes.should.contain(AttributeId("wis") to 20L)

                skills.should.have.size(2)
                skills.should.contain(SkillId("swords") to 30L)
                skills.should.contain(SkillId("spells") to 25L)

                abilities.should.have.size(2)
                abilities.should.contain(AbilityId("concentration"))
                abilities.should.contain(AbilityId("powerstrike"))
            }
        }
    }

    /**
     * Helper to build a Race
     * @param id The ID of the Race
     * @return the Race
     */
    private fun buildRace(id: String,
                          attributes: Map<String, Long> = mapOf(),
                          skills: Map<String, Long> = mapOf(),
                          abilities: Set<String> = setOf()) = Model(
            identity = Identity(
                    id = RaceId(id),
                    version = UUID.randomUUID().toString(),
                    created = Instant.now(),
                    updated = Instant.now()
            ),
            data = RaceData(
                    name = id,
                    description = id,
                    attributeModifiers = attributes.mapKeys { AttributeId(it.key) },
                    skillModifiers = skills.mapKeys { SkillId(it.key) },
                    grantedAbilities = abilities.map(::AbilityId).toSet()
            )
    )

    /**
     * Helper to build a Gender
     * @param id The ID of the Gender
     * @param race The Race of the Gender
     * @return the Gender
     */
    private fun buildGender(id: String,
                            race: String,
                            attributes: Map<String, Long> = mapOf(),
                            skills: Map<String, Long> = mapOf(),
                            abilities: Set<String> = setOf()) = Model(
            identity = Identity(
                    id = GenderId(id),
                    version = UUID.randomUUID().toString(),
                    created = Instant.now(),
                    updated = Instant.now()
            ),
            data = GenderData(
                    name = id,
                    description = id,
                    race = RaceId(race),
                    attributeModifiers = attributes.mapKeys { AttributeId(it.key) },
                    skillModifiers = skills.mapKeys { SkillId(it.key) },
                    grantedAbilities = abilities.map(::AbilityId).toSet()
            )
    )

    /**
     * Helper to build a Class
     * @param id The ID of the Class
     * @return the Class
     */
    private fun buildClass(id: String,
                           attributes: Map<String, Long> = mapOf(),
                           skills: Map<String, Long> = mapOf(),
                           abilities: Set<String> = setOf()) = Model(
            identity = Identity(
                    id = ClassId(id),
                    version = UUID.randomUUID().toString(),
                    created = Instant.now(),
                    updated = Instant.now()
            ),
            data = ClassData(
                    name = id,
                    description = id,
                    attributeModifiers = attributes.mapKeys { AttributeId(it.key) },
                    skillModifiers = skills.mapKeys { SkillId(it.key) },
                    grantedAbilities = abilities.map(::AbilityId).toSet()
            )
    )

    /**
     * Helper to build an Attribute
     * @param id The ID of the Attribute
     * @param value The default value of the Attribute
     * @return the Attribute
     */
    private fun buildAttribute(id: String, value: Long) = Model(
            identity = Identity(
                    id = AttributeId(id),
                    version = UUID.randomUUID().toString(),
                    created = Instant.now(),
                    updated = Instant.now()
            ),
            data = AttributeData(
                    name = id,
                    description = id,
                    defaultValue = value
            )
    )

    /**
     * Helper to build an Skill
     * @param id The ID of the Skill
     * @param value The default value of the Skill
     * @return the Skill
     */
    private fun buildSkill(id: String, value: Long) = Model(
            identity = Identity(
                    id = SkillId(id),
                    version = UUID.randomUUID().toString(),
                    created = Instant.now(),
                    updated = Instant.now()
            ),
            data = SkillData(
                    name = id,
                    description = id,
                    defaultValue = value
            )
    )
}