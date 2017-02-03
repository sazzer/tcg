package uk.co.grahamcox.tcg.webapp.cucumber.converters

/**
 * Type converter to convert to a Long
 */
class IntegerTypeConverter : TypeConverter {
    /**
     * Convert the provided input type
     * @param input The input to convert
     * @return the converted type
     */
    override fun convert(input: String) = input.toInt()
}