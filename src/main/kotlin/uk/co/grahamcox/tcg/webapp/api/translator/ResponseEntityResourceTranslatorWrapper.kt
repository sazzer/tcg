package uk.co.grahamcox.tcg.webapp.api.translator

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.Resource

/**
 * Wrapper around a Resource Translator to produce a ResourceEntity that includes a Last-Modifier and ETag header
 * based on the Updated time and Version fields from the model being translated
 * @param MID The ID type of the Model
 * @param MDATA The Data type of the Model
 * @param RID The ID type of the Resource
 * @param RATTR The Attribute type of the Resource
 * @property translator The translator to wrap
 */
class ResponseEntityResourceTranslatorWrapper<in MID : Id, in MDATA, RID, RATTR>(
        private val translator: ResourceTranslator<MID, MDATA, RID, RATTR>
) {
    fun translate(input: Model<MID, MDATA>) : ResponseEntity<Resource<RID, RATTR>> {
        val translatedResource = translator.translate(input)
        val headers = HttpHeaders()
        headers.lastModified = input.identity.updated.toEpochMilli()
        headers.eTag = """"${input.identity.version}""""
        val response = ResponseEntity(
                translatedResource,
                headers,
                HttpStatus.OK
        )
        return response
    }
}