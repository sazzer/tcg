package uk.co.grahamcox.tcg.webapp

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import uk.co.grahamcox.tcg.model.UnknownResourceException

/**
 * Controller Advice to give a handler for an Invalid Request FIelds exception
 */
@ControllerAdvice
class InvalidRequestFieldHandler {
    /**
     * Handle the exception.
     * This does nothing more than return an HTTP 404
     */
    @ExceptionHandler(InvalidRequestFieldException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleUnknownResource(e: InvalidRequestFieldException) = mapOf(
            "error" to "INVALID_FIELDS",
            "fields" to e.fields
    )
}