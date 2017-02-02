package uk.co.grahamcox.tcg.webapp.attributes

import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.translator.ResourceAttributesTranslator

/**
 * Translator for Attribute data
 */
class AttributeTranslator : ResourceAttributesTranslator<AttributeId, AttributeData, AttributeResourceData> {
    /**
     * Actually translate the model into the resource attributes
     * @param input The input to translate
     * @return the translated resource attributes
     */
    override fun translate(input: Model<AttributeId, AttributeData>) = AttributeResourceData(
            name = input.data.name,
            description = input.data.description
    )
}