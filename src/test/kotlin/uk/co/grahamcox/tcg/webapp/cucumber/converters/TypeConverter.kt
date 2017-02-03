package uk.co.grahamcox.tcg.webapp.cucumber.converters

/**
 * Converter to convert the input type to the desired database one
 */
interface TypeConverter {
    /**
     * Convert the provided input type
     * @param input The input to convert
     * @return the converted type
     */
    fun convert(input: String) : Any
}