package uk.co.grahamcox.tcg.webapp.cucumber.seeder

/**
 * Interface describing how to do more complicated field mappings
 */
interface FieldMapper {
    /**
     * Map the given input onto the target document
     * @param input The input to map
     * @param target The target document to map into
     */
    fun mapField(input: String, target: MutableMap<String, Any>)
}