package uk.co.grahamcox.tcg.webapp.api.demo

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import uk.co.grahamcox.tcg.webapp.api.builder.UriBuilder
import java.net.URI

/**
 * Demo URI Builder
 */
class DemoUriBuilder : UriBuilder<Any> {
    /**
     * Actually build the link
     * @param input The input data to use
     * @return the link
     */
    override fun buildUri(input: Any) = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()
}