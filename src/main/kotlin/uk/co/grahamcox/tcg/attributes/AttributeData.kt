package uk.co.grahamcox.tcg.attributes

/**
 * Representation of a single attribute
 * @property name The name of the attribute
 * @property description The description of the attribute
 * @property defaultValue The default value of this attribute, for new characters
 */
data class AttributeData(
        val name: String,
        val description: String,
        val defaultValue: Long
)