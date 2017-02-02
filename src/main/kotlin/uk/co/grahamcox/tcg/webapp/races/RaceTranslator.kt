package uk.co.grahamcox.tcg.webapp.races

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.webapp.api.translator.ResourceAttributesTranslator

/**
 * Translator for Race data
 */
class RaceTranslator : ResourceAttributesTranslator<RaceId, RaceData, RaceResourceData> {
    /**
     * Actually translate the model into the resource data
     * @param input The input to translate
     * @return the translated resource data
     */
    override fun translate(input: Model<RaceId, RaceData>) = RaceResourceData(
            name = input.data.name,
            description = input.data.description
    )
}