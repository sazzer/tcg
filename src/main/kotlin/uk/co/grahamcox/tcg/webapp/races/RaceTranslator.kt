package uk.co.grahamcox.tcg.webapp.races

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.RaceModel

/**
 * Translator to translate an Race into the API version
 */
class RaceTranslator : ModelTranslator<RaceId, RaceData, RaceModel> {
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<RaceId, RaceData>) =
            RaceModel()
                    .withId(input.identity.id.id)
                    .withName(input.data.name)
                    .withDescription(input.data.description)
}