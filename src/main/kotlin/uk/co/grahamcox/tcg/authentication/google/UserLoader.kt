package uk.co.grahamcox.tcg.authentication.google

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserModel
import uk.co.grahamcox.tcg.user.UserModifier
import uk.co.grahamcox.tcg.user.UserRetriever

/**
 * Helper to load the TCG user from the Google User Profile
 * @property userRetriever The User Retriever to use
 * @property userModifier The User Modifier to use
 */
class UserLoader(private val userRetriever: UserRetriever,
                 private val userModifier: UserModifier) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(UserLoader::class.java)

        /** The name of the Authentication Provider for Google */
        private val GOOGLE_AUTHENTICATION_PROVIDER_NAME = "google"
    }
    /**
     * Load the user that matches the given User Profile. If there isn't one then one will be created automatically
     *
     * @param userProfile The Google User Profile
     * @return the user
     */
    fun loadUserFromProfile(userProfile: UserProfile): UserModel {
        val userModel = userRetriever.retrieveUserByProviderId(GOOGLE_AUTHENTICATION_PROVIDER_NAME, userProfile.id)
        return if (userModel == null) {
            LOG.info("Unknown user has logged in. Creating user record")
            userModifier.createUser(UserData(
                    name = userProfile.displayName,
                    email = userProfile.emails.firstOrNull()?.value
            ), GOOGLE_AUTHENTICATION_PROVIDER_NAME, userProfile.id)
        } else {
            LOG.debug("User {} has logged in", userModel)
            userModel
        }
    }
}