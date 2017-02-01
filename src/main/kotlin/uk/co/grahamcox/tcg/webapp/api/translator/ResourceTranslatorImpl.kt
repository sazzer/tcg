package uk.co.grahamcox.tcg.webapp.api.translator

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.Resource

/**
 * Standard implementation of the [ResourceTranslator]
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The ID type of the Resource
 * @param RATTR The Attribute type of the Resource
 */
class ResourceTranslatorImpl<in MID : Id, in MDATA, out RID, out RATTR>(
        private val resourceDataTranslator: ResourceDataTranslator<MID, MDATA, RID, RATTR>
) : ResourceTranslator<MID, MDATA, RID, RATTR> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(ResourceTranslatorImpl::class.java)
    }
    /**
     * Actually translate the model into the resource
     * @param input The input to translate
     * @return the translated resource
     */
    override fun translate(input: Model<MID, MDATA>): Resource<RID, RATTR> {
        val resourceData = resourceDataTranslator.translate(input)
        val resource = Resource(resourceData)
        LOG.trace("Translated input model {} into Resource {}", resource)
        return resource
    }
}