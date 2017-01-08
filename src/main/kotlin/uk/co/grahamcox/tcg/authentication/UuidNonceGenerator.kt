package uk.co.grahamcox.tcg.authentication

import java.util.*

/**
 * Nonce generator that generates UUIDs
 */
class UuidNonceGenerator : NonceGenerator {
    /**
     * Actually generate the nonce
     * @return the nonce
     */
    override fun generate() = UUID.randomUUID().toString()
}