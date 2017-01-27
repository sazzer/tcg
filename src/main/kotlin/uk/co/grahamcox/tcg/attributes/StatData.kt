package uk.co.grahamcox.tcg.attributes

/**
 * Representation of a single statistic
 * @property name The name of the statistic
 * @property description The description of the statistic
 */
data class StatData(
        val name: String,
        val description: String
)