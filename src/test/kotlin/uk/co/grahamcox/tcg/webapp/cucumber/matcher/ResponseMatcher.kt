package uk.co.grahamcox.tcg.webapp.cucumber.matcher

import org.apache.commons.jxpath.JXPathContext
import uk.co.grahamcox.tcg.webapp.cucumber.requests.Requester

/**
 * Generic means of matching a single response to the expected values
 * @property requester The requester to get the response from
 * @property matcher the matcher to do the actual matching work
 */
class ResponseMatcher(
        private val requester: Requester,
        private val matcher: BeanMatcher) {

    /**
     * Match the provided  details to the last received response
     * @param expected The expected values
     */
    fun match(expected: Map<String, String>) =
            matcher.match(requester.lastResponseBodyAsJson, expected)

    /**
     * Match the provided  details to the last received response, assuming we are matching a single entry in a list
     * @param offset The offset in the list
     * @param expected The expected values
     */
    fun match(offset: Int, expected: Map<String, String>) =
            matcher.match(extractPageEntry(offset), expected)

    /**
     * Helper to extract a single entry from a page of results
     * @param offset The offset in the page
     * @return the entry
     */
    private fun extractPageEntry(offset: Int): Any {
        val responseData = requester.lastResponseBodyAsJson
        val pageJxPathContext = JXPathContext.newContext(responseData)
        return pageJxPathContext.getValue("/entries[$offset]");
    }
}