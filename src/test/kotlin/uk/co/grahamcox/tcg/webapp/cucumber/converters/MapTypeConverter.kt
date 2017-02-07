package uk.co.grahamcox.tcg.webapp.cucumber.converters

import org.slf4j.LoggerFactory

/**
 * Convert a single String into a Map.
 * The individual entries in the map are comma separated, and the key and value for each entry is colon separated.
 * @property valueConverter The value converter to use, if any
 */
class MapTypeConverter(
        private val valueConverter: TypeConverter?
) : TypeConverter {
    companion object {
        /** the logger to use */
        private val LOG = LoggerFactory.getLogger(MapTypeConverter::class.java)
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
                .map { it.split(delimiters = ":", limit = 2)
                        .map(String::trim) }
                .onEach { LOG.debug("Current values: {}", it) }
                .filter { it.size == 2 }
                .map { Pair(it[0], it[1]) }
                .toMap()
                .mapValues {
                    when (valueConverter) {
                        null -> it.value
                        else -> valueConverter.convert(it.value)
                    }
                }
    }
}