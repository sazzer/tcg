package uk.co.grahamcox.tcg.webapp.cucumber.requests

import uk.co.grahamcox.tcg.webapp.cucumber.converters.TypeConverter

/**
 * Mechanism to build a Request Body to send in a request
 * @property fieldMapping The mapping between provided fields names and the ones to use
 * @property typeConverters Any type converters to use for the request fields
 */
class RequestBodyBuilder(
        private val fieldMapping: Map<String, String>,
        private val typeConverters: Map<String, TypeConverter>) {

    /**
     * Actually build the request body
     * @param input The input to build the request body from
     * @return the built request body
     */
    fun buildRequestBody(input: Map<String, String>) : Map<String, Any> {
        val unexpected = input.filterKeys { !fieldMapping.containsKey(it) }
        if (unexpected.isNotEmpty()) {
            throw IllegalArgumentException("Received input fields that we didn't expect: " + unexpected)
        }

        return input.mapKeys { fieldMapping[it.key]!! }
                .mapValues {
                    val converter = typeConverters[it.key]
                    converter?.convert(it.value) ?: it.value
                }
    }
}