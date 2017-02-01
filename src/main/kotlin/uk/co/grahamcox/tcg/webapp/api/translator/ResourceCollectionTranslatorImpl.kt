package uk.co.grahamcox.tcg.webapp.api.translator

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.api.ResourceCollection

/**
 * Standard implementation of [ResourceCollectionLinksTranslator]
 * @property resourceTranslator The translator to use for individual resources
 * @property paginationTranslator The translator to use for pagination controls
 * @property resourceCollectionLinksTranslator The translator to use for resource collection links
 */
class ResourceCollectionTranslatorImpl<in MID : Id, in MDATA, out RID, out RATTR>(
        private val resourceTranslator: ResourceDataTranslator<MID, MDATA, RID, RATTR>,
        private val paginationTranslator: PaginationTranslator<MID, MDATA>,
        private val resourceCollectionLinksTranslator: ResourceCollectionLinksTranslator<MID, MDATA>
) : ResourceCollectionTranslator<MID, MDATA, RID, RATTR> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(ResourceCollectionTranslator::class.java)
    }
    /**
     * Translate the given page into a Resource collection
     * @param input The input to translate
     * @return the translated resource collection
     */
    override fun translate(input: Page<MID, MDATA>): ResourceCollection<RID, RATTR> {
        val pagination = paginationTranslator.translate(input)
        val links = resourceCollectionLinksTranslator.translate(input)
        val resources = input.contents.map { resourceTranslator.translate(it) }

        val resourceCollection = ResourceCollection(
                pagination = pagination,
                links = links,
                data = resources
        )
        LOG.trace("Translated input page {} into Resource Collecton {}", input, resourceCollection)
        return resourceCollection
    }
}