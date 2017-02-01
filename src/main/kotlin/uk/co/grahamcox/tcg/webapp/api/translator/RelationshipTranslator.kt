package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.Relationship

/**
 * Translator to build a Relationship for a model
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 */
interface RelationshipTranslator<in MID : Id, in MDATA> {
    /**
     * Translate the given model into the relationship
     * @param input The input to translate
     * @return the relationship
     */
    fun translate(input: Model<MID, MDATA>) : Relationship
}