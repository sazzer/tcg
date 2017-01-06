package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model

/**
 * Translator to convert a Model object into the Resource Data payload
 * @param MID the ID of the Model
 * @param MDATA The Data of the Model
 * @param RDATA the Data of the API Resource
 */
interface ResourceDataTranslator<in MID : Id, in MDATA, out RDATA> {
    /**
     * Translate the model into the correct Resource Data
     * @param model The model to translate
     * @return the Resource Data for the model
     */
    fun translateResourceData(model: Model<MID, MDATA>) : RDATA
}