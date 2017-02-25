package uk.co.grahamcox.tcg.webapp.cucumber.matcher

import com.winterbe.expekt.should
import org.apache.commons.jxpath.JXPathContext
import uk.co.grahamcox.tcg.webapp.cucumber.converters.TypeConverter

/**
 * Mechanism by which we can match values on arbitrary beans to what is expected
 */
class BeanMatcher(private val fieldMapping: Map<String, String>,
                  private val typeConverters: Map<String, TypeConverter>) {

    /**
     * Match the provided details to the last received response
     * @param input The input to match against
     * @param expected The expected values
     */
    fun match(input: Any, expected: Map<String, String>) {
        checkUnexpectedInputs(expected)

        val jxPathContext = JXPathContext.newContext(input)

        expected.mapValues { typeConverters[it.key]?.convert(it.value) ?: it.value }
                .filterKeys { fieldMapping.containsKey(it) }
                .mapKeys { fieldMapping[it.key] }
                .forEach { field, value ->
                    jxPathContext.getValue(field).should.equal(value)
                }
    }

    /**
     * Check that there is nothing in the provided map of expected values to match against that we didn't expect to see
     * @param expected The expected values
     * @throws IllegalArgumentException if any of these values we didn't expect
     */
    private fun checkUnexpectedInputs(expected: Map<String, String>) {
        val unexpected = expected.filterKeys { !fieldMapping.containsKey(it) }
        if (unexpected.isNotEmpty()) {
            throw IllegalArgumentException("Received match fields that we didn't expect: " + unexpected)
        }
    }
}