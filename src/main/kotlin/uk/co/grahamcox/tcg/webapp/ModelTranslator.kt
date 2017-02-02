package uk.co.grahamcox.tcg.webapp

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model

/**
 * Simple interface to represent a translator from a Model to an API object
 * @param MID the ID of the Model
 * @param MDATA The Data of the Model
 * @param API The API object
 */
interface ModelTranslator<in MID : Id, in MDATA, out API> {
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    fun translate(input: Model<MID, MDATA>) : API
}