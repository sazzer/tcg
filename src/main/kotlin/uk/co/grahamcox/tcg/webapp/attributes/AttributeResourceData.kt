package uk.co.grahamcox.tcg.webapp.attributes

/**
 * Resource data to represent an Attribute
 * @property name the name of the attribute
 * @property description the description of the attribute
 */
data class AttributeResourceData(
        val name: String,
        val description: String
)