package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.RelationshipLinks
import uk.co.grahamcox.tcg.webapp.api.ResourceLinks

/**
 * Translator interface for translating a Model into an API Relationship Links
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 */
interface RelationshipLinksTranslator<in MID : Id, in MDATA> {
    /**
     * Actually translate the model into the relationship links
     * @param input The input to translate
     * @return the translated relationship links
     */
    fun translate(input: Model<MID, MDATA>) : RelationshipLinks
}