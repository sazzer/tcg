package uk.co.grahamcox.tcg.webapp.cucumber.seeder

/**
 * Mechanism to generate a default value for a field
 */
interface FieldDefaulter {
    /**
     * Get the default value for this field
     * @return the default value
     */
    fun getDefault(): Any
}