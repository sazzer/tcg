package uk.co.grahamcox.tcg.webapp.cucumber

import org.slf4j.LoggerFactory
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
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
     * @param params The parameters to the URI
     * @return the last response
     */
    fun get(uri: String, params: Map<String, String> = mapOf()): ResponseEntity<Map<String, Any?>> {
        val realUri = UriComponentsBuilder.fromUriString(uri)
                .replaceQueryParams(LinkedMultiValueMap(params.mapValues { listOf(it.value) }))
                .build()
                .toUriString()

        val headers = HttpHeaders()
        accessToken?.let {
            headers.set("Authorization", "Bearer $it")
        }

        lastResponseEntity = restTemplate.exchange(
                realUri,
                HttpMethod.GET,
                HttpEntity(null, headers),
                Map::class.java)  as ResponseEntity<Map<String, Any?>>
        LOG.debug("Request to {} returned response {}", realUri, lastResponseEntity)
        return lastResponseEntity!!
    }
}