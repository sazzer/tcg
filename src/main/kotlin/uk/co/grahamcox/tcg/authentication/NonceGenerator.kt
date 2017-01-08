package uk.co.grahamcox.tcg.authentication

/**
 * Mechanism to generate a Nonce for use in authentication providers
 */
interface NonceGenerator {
    /**
     * Actually generate the nonce
     * @return the nonce
     */
    fun generate(): String
}