package uk.co.grahamcox.tcg.webapp

/**
 * Exception to indicate that the request has some invalid values in it
 * @property fields The fields that were invalid
 */
class InvalidRequestFieldException(
        val fields: Map<String, Any?>
) : RuntimeException("Invalid values in request")