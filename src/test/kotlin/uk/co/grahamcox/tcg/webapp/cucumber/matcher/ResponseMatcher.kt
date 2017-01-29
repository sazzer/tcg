package uk.co.grahamcox.tcg.webapp.cucumber.matcher

import com.winterbe.expekt.should
import org.apache.commons.jxpath.JXPathContext
import uk.co.grahamcox.tcg.webapp.cucumber.Requester

/**
 * Generic means of matching a single response to the expected values
 */
class ResponseMatcher(
        private val requester: Requester,
        private val fieldMapping: Map<String, String>) {

    /**
     * Match the provided  details to the last received response
     * @param expected The expected values
     */
    fun match(expected: Map<String, String>) {
        val responseData = requester.lastResponseBodyAsJson

        val jxPathContext = JXPathContext.newContext(responseData)

        expected.filterKeys { fieldMapping.containsKey(it) }
                .mapKeys { fieldMapping[it.key] }
                .forEach { field, value ->
                    jxPathContext.getValue(field).should.equal(value)
                }
    }

    /**
     * Match the provided  details to the last received response, assuming we are matching a single entry in a list
     * @param offset The offset in the list
     * @param expected The expected values
     */
    fun match(offset: Int, expected: Map<String, String>) {
        val responseData = requester.lastResponseBodyAsJson

        val pageJxPathContext = JXPathContext.newContext(responseData)
        val jxPathContext =
                pageJxPathContext.getRelativeContext(pageJxPathContext.createPath("/entries[$offset]"))

        expected.filterKeys { fieldMapping.containsKey(it) }
                .mapKeys { fieldMapping[it.key] }
                .forEach { field, value ->
                    jxPathContext.getValue(field).should.equal(value)
                }
    }
}