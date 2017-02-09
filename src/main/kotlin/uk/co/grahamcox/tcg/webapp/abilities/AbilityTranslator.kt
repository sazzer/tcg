package uk.co.grahamcox.tcg.webapp.abilities

import uk.co.grahamcox.tcg.abilities.AbilityData
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.abilities.AbilityModel

/**
 * Translator to translate an Ability into the API version
 */
class AbilityTranslator : ModelTranslator<AbilityId, AbilityData, AbilityModel> {
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<AbilityId, AbilityData>) =
            AbilityModel()
                    .withId(input.identity.id.id)
                    .withName(input.data.name)
                    .withDescription(input.data.description)
}