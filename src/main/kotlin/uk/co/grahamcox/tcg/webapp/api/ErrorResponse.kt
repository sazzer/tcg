package uk.co.grahamcox.tcg.webapp.api

/**
 * Response indicating that an error has occurred
 * @property error The error code
 * @property description The error description
 * @property data Any data about the error
 */
data class ErrorResponse<out PAYLOAD>(
        val error: String,
        val description: String,
        val data: PAYLOAD?
)