package uk.co.grahamcox.tcg.webapp.api.translator

import org.slf4j.LoggerFactory
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

/**
 * Implementation of the [LinkBuilder] that builds links to the current request
 */
class SelfLinkBuilder<in INPUT> : LinkBuilder<INPUT> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(MvcLinkBuilder::class.java)
    }

    /**
     * Translate the input into a URI
     * @param input The input to translate
     * @return the URI
     */
    override fun translate(input: INPUT): URI {
        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri()
        LOG.trace("Built URI {} for input {}", uri, input)
        return uri
    }
}