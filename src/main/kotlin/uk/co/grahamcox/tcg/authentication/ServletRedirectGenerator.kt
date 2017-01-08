package uk.co.grahamcox.tcg.authentication

import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * Implementation of the Redirect Generator to redirect to a particular path on the current servlet
 */
class ServletRedirectGenerator(
        private val path: String
) : RedirectGenerator {
    /**
     * Generate the URI to redirect to
     * @return the URI
     */
    override fun generate() =
        ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(path)
                .replaceQuery("")
                .build()
                .toUri()
}