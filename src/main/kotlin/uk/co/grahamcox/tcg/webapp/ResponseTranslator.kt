package uk.co.grahamcox.tcg.webapp

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.Page

/**
 * Wrapper around a Model Translator that encloses it in a ResponseEntity with ETag and Last Modified set
 * @param MID the ID of the Model
 * @param MDATA The Data of the Model
 * @param API The API object
 * @property modelTranslator The model translator to use
 * @property statusCode The status code to use in the output
 */
class ResponseTranslator<in MID : Id, in MDATA, API> @JvmOverloads constructor(
        private val modelTranslator: ModelTranslator<MID, MDATA, API>,
        private val statusCode: HttpStatus = HttpStatus.OK
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(ResponseTranslator::class.java)
    }
    /**
     * Actually translate the given page of data
     * @param input The page to translate
     * @return the translated page
     */
    fun translate(input: Model<MID, MDATA>) : ResponseEntity<API> {
        val translated = modelTranslator.translate(input)
        val headers = HttpHeaders()
        headers.lastModified = input.identity.updated.toEpochMilli()
        headers.eTag = """"${input.identity.version}""""

        val response = ResponseEntity(translated, headers, statusCode)
        LOG.debug("Translated model {} into response {}", input, response)
        return response
    }
}