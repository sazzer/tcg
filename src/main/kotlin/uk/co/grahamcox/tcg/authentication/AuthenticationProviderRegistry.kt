package uk.co.grahamcox.tcg.authentication

/**
 * Registry of Authentication Providers
 * @property providers The actual providers
 */
class AuthenticationProviderRegistry(private val providers: Map<String, AuthenticationProvider>) {
    /**
     * Get the names of all of the providers
     * @return the list of provider names
     */
    fun getProviderNames() = providers.keys.toList().sorted()

    /**
     * Get the provider with the given name
     * @param name The name of the provider to get
     * @return the provider, or null if it doesn't exist
     */
    fun getProvider(name: String) = providers[name]
}