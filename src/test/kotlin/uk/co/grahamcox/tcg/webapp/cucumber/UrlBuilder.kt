package uk.co.grahamcox.tcg.webapp.cucumber

import org.slf4j.LoggerFactory
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder

/**
 * Mechanism to build a URL from the provided parameters
 * @property baseUrl The base URL to build from
 * @property pathMappings The mappings between Cucumber fields and Path parameters
 * @property queryStringMappings The mappings between Cucumber fields and Query parameters
 */
class UrlBuilder(private val baseUrl: String,
                 private val queryStringMappings: Map<String, String>,
                 private val pathMappings: Map<String, String>) {
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
        val queryString = params.filterKeys { queryStringMappings.containsKey(it)}
                .mapKeys { queryStringMappings[it.key]!! }
                .mapValues { listOf(it.value) }
        LOG.debug("Built query parameters {} from input {}", queryString, params)

        val pathParams = params.filterKeys { pathMappings.containsKey(it)}
                .mapKeys { pathMappings[it.key]!! }
        LOG.debug("Built path parameters {} from input {}", pathParams, params)

        val result = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParams(LinkedMultiValueMap(queryString))
                .build()
                .expand(pathParams)
                .toUriString()
        LOG.debug("Built URL {} from input {}", result, params)
        return result
    }
}