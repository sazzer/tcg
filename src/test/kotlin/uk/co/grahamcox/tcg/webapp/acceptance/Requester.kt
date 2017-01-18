package uk.co.grahamcox.tcg.webapp.acceptance

import org.slf4j.LoggerFactory
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder

/**
 * The mechanism to make HTTP requests to the server under test
 * @property restTemplate The REST Template to use
 */
class Requester(private val restTemplate: TestRestTemplate) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(Requester::class.java)
    }

    /** The last response from the server */
    private var lastResponseEntity: ResponseEntity<String>? = null

    /**
     * Make a GET request to the given URI
     * @param uri The URI
     * @param params The parameters to the URI
     * @return the last response
     */
    fun get(uri: String, params: Map<String, String> = mapOf()): ResponseEntity<String> {
        val realUri = UriComponentsBuilder.fromUriString(uri)
                .replaceQueryParams(LinkedMultiValueMap(params.mapValues { listOf(it.value) }))
                .build()
                .toUriString()
        lastResponseEntity = restTemplate.getForEntity(realUri, String::class.java)
        LOG.debug("Request to {} returned response {}", realUri, lastResponseEntity)
        return lastResponseEntity!!
    }
}