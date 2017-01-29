package uk.co.grahamcox.tcg.webapp.cucumber

import org.slf4j.LoggerFactory
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder

/**
 * Mechanism to build a URL from the provided parameters
 * @property baseUrl The base URL to build from
 */
class UrlBuilder(private val baseUrl: String,
                 private val queryStringMappings: Map<String, String>) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(UrlBuilder::class.java)
    }
    /**
     * Build the URL from the provided parameters
     * @param params the parameters to use
     * @return the URL
     */
    fun build(params: Map<String, String>): String {
        val queryString = params.filterKeys { queryStringMappings.containsKey(it) }
                .mapKeys { queryStringMappings[it.key]!! }
                .mapValues { listOf(it.value) }
        LOG.debug("Built query parameters {} from input {}", queryString, params)

        val result = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParams(LinkedMultiValueMap(queryString))
                .build()
                .toUriString()
        LOG.debug("Built URL {} from input {}", result, params)
        return result
    }
}