package uk.co.grahamcox.tcg.characters

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.attributes.AttributeSort
import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.classes.ClassSort
import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.genders.GenderSort
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.model.UnknownResourceException
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.races.RaceSort
import uk.co.grahamcox.tcg.skills.SkillData
import uk.co.grahamcox.tcg.skills.SkillId

/**
 * Implementation of the Character Creator
 * @property racesRetriever Means to retrieve the Race of the Character
 * @property gendersRetriever Means to retrieve the Gender of the Character
 * @property classesRetriever Means to retrieve the Class of the Character
 * @property attributeRetriever Means to retrieve the Attributes the Character can have
 * @property skillRetriever Means to retrieve the Skills the Character can have
 */
class CharacterCreatorImpl(
        private val racesRetriever: Retriever<RaceId, RaceData, NoFilter, RaceSort>,
        private val gendersRetriever: Retriever<GenderId, GenderData, NoFilter, GenderSort>,
        private val classesRetriever: Retriever<ClassId, ClassData, NoFilter, ClassSort>,
        private val attributeRetriever: Retriever<AttributeId, AttributeData, NoFilter, AttributeSort>,
        private val skillRetriever: Retriever<SkillId, SkillData, NoFilter, AttributeSort>
) : CharacterCreator {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(CharacterCreatorImpl::class.java)
    }

    /**
     * Create a new character matching the given template
     * @param template The template of the character
     */
    override fun createCharacter(template: CharacterTemplate) {
        val race = try {
            racesRetriever.retrieveById(template.race)
        } catch (e: UnknownResourceException) {
            throw UnknownRaceException()
        }
        val gender = try {
            gendersRetriever.retrieveById(template.gender)
        } catch (e: UnknownResourceException) {
            throw UnknownGenderException()
        }
        val cls = try {
            classesRetriever.retrieveById(template.characterClass)
        } catch (e: UnknownResourceException) {
            throw UnknownClassException()
        }

        val attributes = attributeRetriever.list(0, Int.MAX_VALUE, mapOf(), listOf())
                .contents
                .map { Pair(it.identity.id, it.data.defaultValue) }
                .toMap()
                .mapValues { it.value.plus(race.data.attributeModifiers[it.key] ?: 0) }
                .mapValues { it.value.plus(gender.data.attributeModifiers[it.key] ?: 0) }
                .mapValues { it.value.plus(cls.data.attributeModifiers[it.key] ?: 0) }
        val skills = skillRetriever.list(0, Int.MAX_VALUE, mapOf(), listOf())
                .contents
                .map { Pair(it.identity.id, it.data.defaultValue) }
                .toMap()
                .mapValues { it.value.plus(race.data.skillModifiers[it.key] ?: 0) }
                .mapValues { it.value.plus(gender.data.skillModifiers[it.key] ?: 0) }
                .mapValues { it.value.plus(cls.data.skillModifiers[it.key] ?: 0) }
        val abilities = race.data.grantedAbilities + gender.data.grantedAbilities + cls.data.grantedAbilities

        LOG.debug("Creating character {}", template)
        LOG.debug("Race={}", race)
        LOG.debug("Gender={}", gender)
        LOG.debug("Class={}", cls)
        LOG.debug("Attributes={}", attributes)
        LOG.debug("Skills={}", skills)
        LOG.debug("Abilities={}", abilities)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}