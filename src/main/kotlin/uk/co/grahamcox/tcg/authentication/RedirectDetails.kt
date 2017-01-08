package uk.co.grahamcox.tcg.authentication

import java.net.URI

/**
 * Details of the redirect for authentication
 * @property uri The URI to redirect to
 */
data class RedirectDetails(
        val uri: URI
)