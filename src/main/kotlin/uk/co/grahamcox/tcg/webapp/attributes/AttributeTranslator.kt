package uk.co.grahamcox.tcg.webapp.attributes

import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.AttributeModel
import uk.co.grahamcox.tcg.webapp.model.IdentityModel

/**
 * Translator to translate an Attribute into the API version
 */
class AttributeTranslator : ModelTranslator<AttributeId, AttributeData, AttributeModel> {
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<AttributeId, AttributeData>) =
            AttributeModel()
                    .withName(input.data.name)
                    .withDescription(input.data.description)
                    .withIdentity(IdentityModel()
                            .withId(input.identity.id.id)
                            .withVersion(input.identity.version)
                            .withCreated(input.identity.created)
                            .withUpdated(input.identity.updated)
                    )
}