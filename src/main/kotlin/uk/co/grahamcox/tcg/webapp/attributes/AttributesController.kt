package uk.co.grahamcox.tcg.webapp.attributes

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.attributes.AttributeSort
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.webapp.api.Resource
import uk.co.grahamcox.tcg.webapp.api.ResourceCollection
import uk.co.grahamcox.tcg.webapp.api.translator.ResourceCollectionTranslator
import uk.co.grahamcox.tcg.webapp.api.translator.ResponseEntityResourceTranslatorWrapper
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing attributes
 * @property attributesRetriever Retriever for loading Attributes
 * @property resourceTranslator Translator for translating individual attributes
 * @property resourceCollectionTranslator Translator for translating pages of attributes
 */
@RestController
@RequestMapping("/api/attributes")
class AttributesController(
        private val attributesRetriever: Retriever<AttributeId, AttributeData, NoFilter, AttributeSort>,
        private val resourceTranslator: ResponseEntityResourceTranslatorWrapper<AttributeId, AttributeData, String, AttributeResourceData>,
        private val resourceCollectionTranslator: ResourceCollectionTranslator<AttributeId, AttributeData, String, AttributeResourceData>) {

    /**
     * Get a list of the attributes in the system
     * @param offset The offset to start listing from. Default of 0
     * @param count The number of records to get. Default of 10
     * @param sort The order to sort the records by. Defaults by name
     * @return the page of results
     */
    @RequestMapping
    fun getAttributes(@RequestParam(value = "offset", defaultValue = "0", required = false) offset: Int,
                      @RequestParam(value = "count", defaultValue = "10", required = false) count: Int,
                      @RequestParam(value = "sort", defaultValue = "", required = false) sort: String):
            ResourceCollection<String, AttributeResourceData> {
        val results = attributesRetriever.list(
                offset,
                count,
                mapOf(),
                parseSorts(sort)
        )

        return resourceCollectionTranslator.translate(results)
    }

    /**
     * Get the requested attribute
     * @param attributeId The ID of the attribute to retriever
     * @return the attribute
     */
    @RequestMapping("/{id}")
    fun getAttribute(@PathVariable("id") attributeId: String): ResponseEntity<Resource<String, AttributeResourceData>> {
        val attribute = attributesRetriever.retrieveById(AttributeId(attributeId))

        return resourceTranslator.translate(attribute)
    }
}