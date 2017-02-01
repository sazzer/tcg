package uk.co.grahamcox.tcg.webapp.api.translator

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.ResourceData

/**
 * Standard implementation of the [ResourceDataTranslator]
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The ID type of the Resource
 * @param RATTR The Attribute type of the Resource
 */
class ResourceDataTranslatorImpl<in MID : Id, in MDATA, out RID, out RATTR>(
        private val resourceIdentityTranslator: ResourceIdentityTranslator<MID, RID>,
        private val resourceAttributesTranslator: ResourceAttributesTranslator<MID, MDATA, RATTR>,
        private val resourceLinksTranslator: ResourceLinksTranslator<MID, MDATA>,
        private val relationshipTranslators: Map<String, RelationshipTranslator<MID, MDATA>>?
) : ResourceDataTranslator<MID, MDATA, RID, RATTR> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(ResourceDataTranslatorImpl::class.java)
    }
    /**
     * Actually translate the model into the resource data
     * @param input The input to translate
     * @return the translated resource data
     */
    override fun translate(input: Model<MID, MDATA>): ResourceData<RID, RATTR> {
        val identity = resourceIdentityTranslator.translate(input.identity)
        val attributes = resourceAttributesTranslator.translate(input)
        val links = resourceLinksTranslator.translate(input)
        val relationships = relationshipTranslators?.mapValues { it.value.translate(input) }

        val resourceData = ResourceData(
                id = identity,
                attributes = attributes,
                links = links,
                relationships = relationships
        )
        LOG.trace("Translated input model {} into Resource Data {}", input, resourceData)
        return resourceData
    }
}