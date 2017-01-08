package uk.co.grahamcox.tcg.authentication

import java.net.URI

/**
 * Mechanism to generate a URI to rediect to
 */
interface RedirectGenerator {
    /**
     * Generate the URI to redirect to
     * @return the URI
     */
    fun generate(): URI
}