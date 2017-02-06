package uk.co.grahamcox.tcg.webapp.skills

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.skills.SkillData
import uk.co.grahamcox.tcg.skills.SkillId
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.SkillModel

/**
 * Translator to translate an Skill into the API version
 */
class SkillTranslator : ModelTranslator<SkillId, SkillData, SkillModel> {
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<SkillId, SkillData>) =
            SkillModel()
                    .withId(input.identity.id.id)
                    .withName(input.data.name)
                    .withDescription(input.data.description)
                    .withDefault(input.data.defaultValue)
}