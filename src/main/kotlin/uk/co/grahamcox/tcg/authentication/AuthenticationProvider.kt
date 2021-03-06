package uk.co.grahamcox.tcg.authentication

import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId

/**
 * Interface describing an Authentication Provider
 */
interface AuthenticationProvider {
    /**
     * Start authentication for this provider
     * @return the details of the redirect to the provider
     */
    fun start(): RedirectDetails

    /**
     * Handle the callback from the provider after authentication has finished
     * @param params The parameters from the provider
     * @return the logged in user
     */
    fun handleCallback(params: Map<String, Any>): Model<UserId, UserData>
}