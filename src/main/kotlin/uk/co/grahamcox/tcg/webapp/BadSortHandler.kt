package uk.co.grahamcox.tcg.webapp

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import uk.co.grahamcox.tcg.model.UnknownResourceException

/**
 * Controller Advice to give a handler for an SortParseException
 */
@ControllerAdvice
class BadSortHandler {
    /**
     * Handle the exception.
     * This does nothing more than return an HTTP 400 with a payload that indicates which sort was bad
     */
    @ExceptionHandler(SortParseException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleUnknownResource(e: SortParseException) = mapOf(
            "error" to "INVALID_SORT",
            "sort" to e.badSort
    )
}