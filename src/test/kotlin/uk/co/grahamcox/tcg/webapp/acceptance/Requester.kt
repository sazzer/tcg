package uk.co.grahamcox.tcg.webapp.acceptance

import org.slf4j.LoggerFactory
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

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

    /** The last response, in a safe manner */
    val lastResponse: ResponseEntity<String>
        get() = lastResponseEntity ?: throw AssertionError("No last response has been received")

    /**
     * Make a GET request to the given URI
     * @param uri The URI
     * @return the last response
     */
    fun get(uri: String): ResponseEntity<String> {
        lastResponseEntity = restTemplate.getForEntity(uri, String::class.java)
        LOG.debug("Request to {} returned response {}", uri, lastResponseEntity)
        return lastResponseEntity!!
    }
}