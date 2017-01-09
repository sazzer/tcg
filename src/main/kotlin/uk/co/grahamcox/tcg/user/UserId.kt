package uk.co.grahamcox.tcg.user

import uk.co.grahamcox.tcg.model.Id

/**
 * The ID of a User
 * @property id The ID
 */
data class UserId(override val id: String): Id