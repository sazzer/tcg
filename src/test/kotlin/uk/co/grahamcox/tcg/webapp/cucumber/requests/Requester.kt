package uk.co.grahamcox.tcg.webapp.cucumber.requests

import org.slf4j.LoggerFactory
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
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
    private var lastResponseEntity: ResponseEntity<Map<String, Any?>>? = null

    /** Get the last repsonse in a safe manner */
    val lastResponse: ResponseEntity<Map<String, Any?>>
        get() = lastResponseEntity ?: throw IllegalStateException("No request has been recorded")

    /** Get the body of the last response, assuming it's a JSON Object */
    val lastResponseBodyAsJson: Map<String, Any?>
        get() = lastResponse.body

    /** The access token to use, if any */
    var accessToken: String? = null
    /**
     * Make a GET request to the given URI
     * @param uri The URI
     * @return the last response
     */
    fun get(uri: String) = makeRequest(uri, HttpMethod.GET, null)

    /**
     * Make a POST request to the given URI
     * @param uri The URI to make a POST to
     * @param body The body to post
     * @return the last response
     */
    fun post(uri: String, body: Any) = makeRequest(uri, HttpMethod.POST, body)

    /**
     * Actually make a request and retrive the response
     * @param uri The URI to make the request to
     * @param method The HTTP Method to use for the request
     * @param body The body to send, if any
     */
    private fun makeRequest(uri: String, method: HttpMethod, body: Any?) : ResponseEntity<Map<String, Any?>> {
        lastResponseEntity = restTemplate.exchange(
                uri,
                method,
                HttpEntity(body, buildHeaders()),
                Map::class.java)  as ResponseEntity<Map<String, Any?>>

        LOG.debug("Request to {} {} with payload {} returned response {}",
                method, uri, body, lastResponseEntity)
        return lastResponseEntity!!
    }
    /**
     * Helper to build the headers needed for this request
     * @return the HTTP Headers
     */
    private fun buildHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        accessToken?.let {
            headers.set("Authorization", "Bearer $it")
        }
        return headers
    }
}