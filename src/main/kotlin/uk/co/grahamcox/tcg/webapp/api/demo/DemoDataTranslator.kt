package uk.co.grahamcox.tcg.webapp.api.demo

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.builder.ResourceDataTranslator

/**
 * Demo Data Translator
 */
class DemoDataTranslator : ResourceDataTranslator<DemoId, DemoModelData, DemoData> {
    /**
     * Translate the model into the correct Resource Data
     * @param model The model to translate
     * @return the Resource Data for the model
     */
    override fun translateResourceData(model: Model<DemoId, DemoModelData>) = DemoData(
            name = model.data.name,
            age = model.data.age
    )
}