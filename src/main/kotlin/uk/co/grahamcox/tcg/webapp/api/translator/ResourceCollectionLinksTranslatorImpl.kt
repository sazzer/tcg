package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Page
import uk.co.grahamcox.tcg.webapp.api.ResourceCollectionLinks

/**
 * Standard implementation of the [ResourceCollectionLinksTranslator]
 */
class ResourceCollectionLinksTranslatorImpl<in MID : Id, in MDATA>(
        private val selfTranslator: LinkBuilder<Page<MID, MDATA>>
) : ResourceCollectionLinksTranslator<MID, MDATA> {
    /**
     * Actually translate the model into the resource links
     * @param input The input to translate
     * @return the translated resource links
     */
    override fun translate(input: Page<MID, MDATA>) = ResourceCollectionLinks(
            self = selfTranslator.translate(input)
    )
}