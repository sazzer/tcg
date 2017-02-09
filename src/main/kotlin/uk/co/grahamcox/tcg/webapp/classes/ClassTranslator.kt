package uk.co.grahamcox.tcg.webapp.classes

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.classes.AttributesModel
import uk.co.grahamcox.tcg.webapp.model.classes.ClassModel
import uk.co.grahamcox.tcg.webapp.model.classes.SkillsModel

/**
 * Translator to translate an Class into the API version
 */
class ClassTranslator : ModelTranslator<ClassId, ClassData, ClassModel> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(ClassTranslator::class.java)
    }

    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<ClassId, ClassData>): ClassModel {
        val attributes = AttributesModel()
        input.data.attributeModifiers.mapKeys { it.key.id }
                .forEach { k, v -> attributes.withAdditionalProperty(k, v) }

        val skills = SkillsModel()
        input.data.skillModifiers.mapKeys { it.key.id }
                .forEach { k, v -> skills.withAdditionalProperty(k, v) }

        val cls = ClassModel()
                .withId(input.identity.id.id)
                .withName(input.data.name)
                .withDescription(input.data.description)
                .withAttributes(attributes)
                .withSkills(skills)
                .withAbilities(input.data.grantedAbilities.map { it.id }.toSet())
        LOG.debug("Translated input {} into API object {}", input, cls)
        return cls
    }
}