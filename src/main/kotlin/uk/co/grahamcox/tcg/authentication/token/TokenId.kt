package uk.co.grahamcox.tcg.authentication.token

import uk.co.grahamcox.tcg.model.Id

/**
 * The ID of an Access Token
 * @property id The actual ID
 */
data class TokenId(override val id: String) : Id