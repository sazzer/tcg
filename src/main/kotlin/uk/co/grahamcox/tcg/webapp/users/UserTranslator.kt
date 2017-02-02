package uk.co.grahamcox.tcg.webapp.users

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.webapp.api.translator.ResourceAttributesTranslator

/**
 * Translator for User data
 */
class UserTranslator : ResourceAttributesTranslator<UserId, UserData, UserResourceData> {
    /**
     * Actually translate the model into the resource data
     * @param input The input to translate
     * @return the translated resource data
     */
    override fun translate(input: Model<UserId, UserData>) = UserResourceData(
            name = input.data.name,
            email = input.data.email
    )
}