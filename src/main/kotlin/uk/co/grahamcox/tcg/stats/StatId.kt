package uk.co.grahamcox.tcg.stats

import uk.co.grahamcox.tcg.model.Id

/**
 * The ID of a Stat
 * @property id The actual ID
 */
data class StatId(override val id: String): Id