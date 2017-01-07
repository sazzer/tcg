package uk.co.grahamcox.tcg.webapp.cucumber.steps

import org.slf4j.LoggerFactory
import org.springframework.boot.test.web.client.TestRestTemplate
import java.net.URI

/**
 * The mechanism to make HTTP requests to the server under test
 * @property restTemplate The REST Template to use
 */
class Requester(private val restTemplate: TestRestTemplate) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(Requester::class.java)
    }

    /**
     * Make a GET request to the given URI
     * @param uri The URI
     */
    fun get(uri: String) {
        val response = restTemplate.getForEntity(uri, String::class.java)
        LOG.debug("Request to {} returned response {}", uri, response)
    }
}