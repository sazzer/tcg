package uk.co.grahamcox.tcg.attributes

/**
 * Representation of a single attribute
 * @property name The name of the attribute
 * @property description The description of the attribute
 */
data class AttributeData(
        val name: String,
        val description: String
)