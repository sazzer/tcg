package uk.co.grahamcox.tcg.attributes

import uk.co.grahamcox.tcg.model.Id

/**
 * The ID of an attribute
 * @property id The actual ID
 */
data class AttributeId(override val id: String): Id