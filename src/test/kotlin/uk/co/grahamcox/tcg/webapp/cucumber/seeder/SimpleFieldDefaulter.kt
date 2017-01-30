package uk.co.grahamcox.tcg.webapp.cucumber.seeder

/**
 * Simple implementation of the Field Defaulter to generate a static value
 * @property value The value to generate
 */
class SimpleFieldDefaulter(private val value: Any) : FieldDefaulter {
    /**
     * Get the default value for this field
     * @return the default value
     */
    override fun getDefault() = value
}