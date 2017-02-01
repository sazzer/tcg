package uk.co.grahamcox.tcg.webapp.cucumber.matcher

import com.winterbe.expekt.should
import org.apache.commons.jxpath.JXPathContext
import uk.co.grahamcox.tcg.webapp.cucumber.Requester

/**
 * Generic means of matching a single response to the expected values
 * @property requester The requester to get the response from
 * @property fieldMapping The mappings between Cucumber and Response fields
 * @property singleMatchPrefix The prefix on the field paths for single matches
 * @property collectionPrefix The prefix on the field paths for the collection matches, excluding the index
 */
class ResponseMatcher(
        private val requester: Requester,
        private val fieldMapping: Map<String, String>,
        private val singleMatchPrefix: String?,
        private val collectionPrefix: String) {

    /**
     * Match the provided  details to the last received response
     * @param expected The expected values
     */
    fun match(expected: Map<String, String>) {
        val responseData = requester.lastResponseBodyAsJson

        val jxPathContext = JXPathContext.newContext(responseData)
        val innerJxPathContext = when (singleMatchPrefix) {
            null -> jxPathContext
            else -> jxPathContext.getRelativeContext(jxPathContext.createPath(singleMatchPrefix))
        }

        expected.filterKeys { fieldMapping.containsKey(it) }
                .mapKeys { fieldMapping[it.key] }
                .forEach { field, value ->
                    innerJxPathContext.getValue(field).should.equal(value)
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
                pageJxPathContext.getRelativeContext(pageJxPathContext.createPath("$collectionPrefix[$offset]"))

        expected.filterKeys { fieldMapping.containsKey(it) }
                .mapKeys { fieldMapping[it.key] }
                .forEach { field, value ->
                    jxPathContext.getValue(field).should.equal(value)
                }
    }
}