package uk.co.grahamcox.tcg.webapp.genders

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.genders.GenderModel
import uk.co.grahamcox.tcg.webapp.model.genders.AttributesModel
import uk.co.grahamcox.tcg.webapp.model.genders.SkillsModel

/**
 * Translator to translate an Gender into the API version
 */
class GenderTranslator : ModelTranslator<GenderId, GenderData, GenderModel> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(GenderTranslator::class.java)
    }
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<GenderId, GenderData>): GenderModel {
        val attributes = AttributesModel()
        input.data.attributeModifiers.mapKeys { it.key.id }
                .forEach { k, v -> attributes.withAdditionalProperty(k, v) }

        val skills = SkillsModel()
        input.data.skillModifiers.mapKeys { it.key.id }
                .forEach { k, v -> skills.withAdditionalProperty(k, v) }

        val gender = GenderModel()
                .withId(input.identity.id.id)
                .withName(input.data.name)
                .withDescription(input.data.description)
                .withRace(input.data.race.id)
                .withAttributes(attributes)
                .withSkills(skills)
                .withAbilities(input.data.grantedAbilities.map { it.id }.toSet())
        LOG.debug("Translated input {} into API object {}", input, gender)
        return gender
    }
}