package uk.co.grahamcox.tcg.webapp.api.translator

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.SingleRelationship

/**
 * Standard implementation of the [RelationshipTranslator] for relationships to single resources
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The type of the relationship ID
 */
class SingleRelationshipTranslatorImpl<in MID : Id, in MDATA, out RID>(
        private val relationshipDataBuilder: RelationshipDataBuilder<MID, MDATA, RID>,
        private val relationshipLinksTranslator: RelationshipLinksTranslator<MID, MDATA>
) : RelationshipTranslator<MID, MDATA> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(SingleRelationshipTranslatorImpl::class.java)
    }
    /**
     * Translate the given model into the relationship
     * @param input The input to translate
     * @return the relationship
     */
    override fun translate(input: Model<MID, MDATA>): SingleRelationship<RID> {
        val relationship = SingleRelationship(
                data = relationshipDataBuilder.buildRelationshipData(input),
                links = relationshipLinksTranslator.translate(input)
        )
        LOG.trace("Translated input model {} into Resource {}", input, relationship)
        return relationship
    }
}