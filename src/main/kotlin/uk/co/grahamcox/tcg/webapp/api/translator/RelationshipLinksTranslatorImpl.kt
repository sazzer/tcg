package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.RelationshipLinks
import uk.co.grahamcox.tcg.webapp.api.ResourceLinks

/**
 * Standard implementation of the [ResourceLinksTranslator]
 * @property selfTranslator The translator to use for building the Self link
 */
class RelationshipLinksTranslatorImpl<in MID : Id, in MDATA>(
        private val selfTranslator: LinkBuilder<Model<MID, MDATA>>
) : RelationshipLinksTranslator<MID, MDATA> {
    /**
     * Actually translate the model into the resource links
     * @param input The input to translate
     * @return the translated resource links
     */
    override fun translate(input: Model<MID, MDATA>) = RelationshipLinks(
            self = selfTranslator.translate(input)
    )
}