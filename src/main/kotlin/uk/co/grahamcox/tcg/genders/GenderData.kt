package uk.co.grahamcox.tcg.genders

import uk.co.grahamcox.tcg.races.RaceId

/**
 * Representation of a single gender
 * @property name The name of the gender
 * @property description The description of the gender
 * @property race The Race that the Gender relates to
 */
data class GenderData(
        val name: String,
        val description: String,
        val race: RaceId
)