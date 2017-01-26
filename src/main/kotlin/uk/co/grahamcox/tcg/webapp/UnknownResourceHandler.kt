package uk.co.grahamcox.tcg.webapp

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import uk.co.grahamcox.tcg.model.UnknownResourceException

/**
 * Controller Advice to give a handler for an Unknown Resource exception
 */
@ControllerAdvice
class UnknownResourceHandler {
    /**
     * Handle the exception.
     * This does nothing more than return an HTTP 404
     */
    @ExceptionHandler(UnknownResourceException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUnknownResource() {

    }
}