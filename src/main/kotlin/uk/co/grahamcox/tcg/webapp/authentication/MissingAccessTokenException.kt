package uk.co.grahamcox.tcg.webapp.authentication

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Indication that an Access Token was required but not present in the request
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
class MissingAccessTokenException : RuntimeException("No access token was present in the request")