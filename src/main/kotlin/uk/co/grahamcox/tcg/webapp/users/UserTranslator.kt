package uk.co.grahamcox.tcg.webapp.users

import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.ModelTranslator
import uk.co.grahamcox.tcg.webapp.model.UserModel
import uk.co.grahamcox.tcg.webapp.model.IdentityModel

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
                    .withName(input.data.name)
                    .withEmail(input.data.email)
                    .withIdentity(IdentityModel()
                            .withId(input.identity.id.id)
                            .withVersion(input.identity.version)
                            .withCreated(input.identity.created)
                            .withUpdated(input.identity.updated)
                    )
}