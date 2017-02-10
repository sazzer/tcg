package uk.co.grahamcox.tcg.webapp.characters

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.characters.CharacterData
import uk.co.grahamcox.tcg.characters.CharacterId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.characters.AttributesModel
import uk.co.grahamcox.tcg.webapp.model.characters.CharacterModel
import uk.co.grahamcox.tcg.webapp.model.characters.SkillsModel


/**
 * Translator to translate a Character into the API version
 */
class CharacterTranslator : ModelTranslator<CharacterId, CharacterData, CharacterModel> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(CharacterTranslator::class.java)
    }
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<CharacterId, CharacterData>): CharacterModel {
        val attributes = AttributesModel()
        input.data.attributes.mapKeys { it.key.id }
                .forEach { k, v -> attributes.withAdditionalProperty(k, v) }

        val skills = SkillsModel()
        input.data.skills.mapKeys { it.key.id }
                .forEach { k, v -> skills.withAdditionalProperty(k, v) }

        val char = CharacterModel()
                .withId(input.identity.id.id)
                .withOwner(input.data.owner.id)
                .withName(input.data.name)
                .withRace(input.data.race.id)
                .withGender(input.data.gender.id)
                .withClass(input.data.characterClass.id)
                .withAttributes(attributes)
                .withSkills(skills)
                .withAbilities(input.data.abilities.map { it.id }.toSet())
        LOG.debug("Translated input {} into API object {}", input, char)
        return char
    }
}