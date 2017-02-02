package uk.co.grahamcox.tcg.classes

import uk.co.grahamcox.tcg.model.Id

/**
 * The ID of an class
 * @property id The actual ID
 */
data class ClassId(override val id: String): Id