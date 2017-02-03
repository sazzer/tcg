package uk.co.grahamcox.tcg.characters

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

/**
 * Implementation of the Character Creator
 * @property racesRetriever Means to retrieve the Race of the Character
 * @property gendersRetriever Means to retrieve the Gender of the Character
 * @property classesRetriever Means to retrieve the Class of the Character
 */
class CharacterCreatorImpl(
        private val racesRetriever: Retriever<RaceId, RaceData, NoFilter, RaceSort>,
        private val gendersRetriever: Retriever<GenderId, GenderData, NoFilter, GenderSort>,
        private val classesRetriever: Retriever<ClassId, ClassData, NoFilter, ClassSort>
) : CharacterCreator {
    /**
     * Create a new character matching the given template
     * @param template The template of the character
     */
    override fun createCharacter(template: CharacterTemplate) {
        var race = try {
            racesRetriever.retrieveById(template.race)
        } catch (e: UnknownResourceException) {
            throw UnknownRaceException()
        }
        var gender = try {
            gendersRetriever.retrieveById(template.gender)
        } catch (e: UnknownResourceException) {
            throw UnknownGenderException()
        }
        var cls = try {
            classesRetriever.retrieveById(template.characterClass)
        } catch (e: UnknownResourceException) {
            throw UnknownClassException()
        }

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}