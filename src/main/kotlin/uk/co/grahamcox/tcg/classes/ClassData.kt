package uk.co.grahamcox.tcg.classes

/**
 * Representation of a single class
 * @property name The name of the class
 * @property description The description of the class
 */
data class ClassData(
        val name: String,
        val description: String
)