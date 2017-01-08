package uk.co.grahamcox.tcg.authentication

/**
 * Interface describing an Authentication Provider
 */
interface AuthenticationProvider {
    /**
     * Start authentication for this provider
     */
    fun start(): RedirectDetails
}