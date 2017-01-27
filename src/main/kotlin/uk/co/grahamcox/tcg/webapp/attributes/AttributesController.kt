package uk.co.grahamcox.tcg.webapp.attributes

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.attributes.AttributeSort
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.SortOrder
import uk.co.grahamcox.tcg.webapp.model.AttributeModel
import uk.co.grahamcox.tcg.webapp.model.IdentityModel
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.PaginationModel
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing attributes
 */
@RestController
@RequestMapping("/api/attributes")
class AttributesController(private val attributesRetriever: Retriever<AttributeId, AttributeData, AttributeSort>) {
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
                      @RequestParam(value = "sort", defaultValue = "", required = false) sort: String): PageModel {
        val results = attributesRetriever.list(
                offset,
                count,
                parseSorts(sort)
        )

        return PageModel()
                .withPagination(PaginationModel()
                        .withOffset(results.offset.toLong())
                        .withTotal(results.totalCount.toLong()))
                .withEntries(results.contents.map { translateModel(it) })
    }

    /**
     * Get the requested attribute
     * @param attributeId The ID of the attribute to retriever
     * @return the attribute
     */
    @RequestMapping("/{id}")
    fun getAttribute(@PathVariable("id") attributeId: String): AttributeModel {
        val attribute = attributesRetriever.retrieveById(AttributeId(attributeId))

        return translateModel(attribute)
    }

    private fun translateModel(model: Model<AttributeId, AttributeData>) =
            AttributeModel()
                    .withName(model.data.name)
                    .withDescription(model.data.description)
                    .withIdentity(IdentityModel()
                            .withId(model.identity.id.id)
                            .withVersion(model.identity.version)
                            .withCreated(model.identity.created)
                            .withUpdated(model.identity.updated)
                    )
}