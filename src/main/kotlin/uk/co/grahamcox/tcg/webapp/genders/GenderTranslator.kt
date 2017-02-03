package uk.co.grahamcox.tcg.webapp.genders

import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.GenderModel

/**
 * Translator to translate an Gender into the API version
 */
class GenderTranslator : ModelTranslator<GenderId, GenderData, GenderModel> {
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<GenderId, GenderData>) =
            GenderModel()
                    .withId(input.identity.id.id)
                    .withName(input.data.name)
                    .withDescription(input.data.description)
                    .withRace(input.data.race.id)
}