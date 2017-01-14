package uk.co.grahamcox.tcg.authentication.google

import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.user.UserModel
import uk.co.grahamcox.tcg.user.UserRetriever

/**
 * Helper to load the TCG user from the Google User Profile
 * @property userRetriever The User Retriever to use
 */
class UserLoader(private val userRetriever: UserRetriever) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(UserLoader::class.java)
    }
    /**
     * Load the user that matches the given User Profile. If there isn't one then one will be created automatically
     *
     * @param userProfile The Google User Profile
     * @return the user
     */
    fun loadUserFromProfile(userProfile: UserProfile): UserModel? {
        val userModel = userRetriever.retrieveUserByProviderId("google", userProfile.id)
        if (userModel == null) {
            LOG.info("Unknown user has logged in. Creating user record")
        } else {
            LOG.debug("User {} has logged in", userModel)
        }
        return userModel
    }
}