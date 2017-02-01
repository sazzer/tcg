package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Id

/**
 * Test ID Class
 * @property id The ID
 */
data class TestId(override val id: String) : Id