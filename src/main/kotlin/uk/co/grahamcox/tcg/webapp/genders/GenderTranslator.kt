package uk.co.grahamcox.tcg.webapp.genders

import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.translator.ResourceAttributesTranslator

/**
 * Translator for Gender data
 */
class GenderTranslator : ResourceAttributesTranslator<GenderId, GenderData, GenderResourceData> {
    /**
     * Actually translate the model into the resource attributes
     * @param input The input to translate
     * @return the translated resource attributes
     */
    override fun translate(input: Model<GenderId, GenderData>) = GenderResourceData(
            name = input.data.name,
            description = input.data.description
    )
}