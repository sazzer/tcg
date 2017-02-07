package uk.co.grahamcox.tcg.webapp.cucumber.converters

import org.slf4j.LoggerFactory

/**
 * Convert a single String into a List.
 * The individual entries in the list are comma separated..
 */
class ListTypeConverter : TypeConverter {
    companion object {
        /** the logger to use */
        private val LOG = LoggerFactory.getLogger(ListTypeConverter::class.java)
    }
    /**
     * Convert the provided input type
     * @param input The input to convert
     * @return the converted type
     */
    override fun convert(input: String): Any {
        return input.split(",")
                .map(String::trim)
                .onEach { LOG.debug("Current values: {}", it) }
                .toList()
    }
}