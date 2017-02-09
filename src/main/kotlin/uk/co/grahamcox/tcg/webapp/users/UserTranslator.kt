package uk.co.grahamcox.tcg.webapp.users

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.users.UserModel

/**
 * Translator to translate an User into the API version
 */
class UserTranslator : ModelTranslator<UserId, UserData, UserModel> {
    /**
     * Actually translate the model object
     * @param input The input to translate
     * @return the translated API object
     */
    override fun translate(input: Model<UserId, UserData>) =
            UserModel()
                    .withId(input.identity.id.id)
                    .withName(input.data.name)
                    .withEmail(input.data.email)
}