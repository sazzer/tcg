package uk.co.grahamcox.tcg.webapp.races

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.races.AttributesModel
import uk.co.grahamcox.tcg.webapp.model.races.RaceModel
import uk.co.grahamcox.tcg.webapp.model.races.SkillsModel

/**
 * Translator to translate an Race into the API version
 */
class RaceTranslator : ModelTranslator<RaceId, RaceData, RaceModel> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(RaceTranslator::class.java)
    }

    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<RaceId, RaceData>): RaceModel {
        val attributes = AttributesModel()
        input.data.attributeModifiers.mapKeys { it.key.id }
                .forEach { k, v -> attributes.withAdditionalProperty(k, v) }

        val skills = SkillsModel()
        input.data.skillModifiers.mapKeys { it.key.id }
                .forEach { k, v -> skills.withAdditionalProperty(k, v) }

        val race = RaceModel()
                .withId(input.identity.id.id)
                .withName(input.data.name)
                .withDescription(input.data.description)
                .withAttributes(attributes)
                .withSkills(skills)
                .withAbilities(input.data.grantedAbilities.map { it.id }.toSet())
        LOG.debug("Translated input {} into API object {}", input, race)
        return race
    }
}