package uk.co.grahamcox.tcg.webapp.classes

import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.classes.ClassModel

/**
 * Translator to translate an Class into the API version
 */
class ClassTranslator : ModelTranslator<ClassId, ClassData, ClassModel> {
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<ClassId, ClassData>) =
            ClassModel()
                    .withId(input.identity.id.id)
                    .withName(input.data.name)
                    .withDescription(input.data.description)
}