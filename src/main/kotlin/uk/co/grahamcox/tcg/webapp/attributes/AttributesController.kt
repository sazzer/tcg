package uk.co.grahamcox.tcg.webapp.attributes

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.webapp.model.AttributeModel
import uk.co.grahamcox.tcg.webapp.model.IdentityModel


/**
 * Controller for accessing attributes
 */
@RestController
@RequestMapping("/api/attributes")
class AttributesController(private val attributesRetriever: Retriever<AttributeId, AttributeData>) {
    /**
     * Get the requested attribute
     * @param attributeId The ID of the attribute to retriever
     * @return the attribute
     */
    @RequestMapping("/{id}")
    fun getAttribute(@PathVariable("id") attributeId: String): AttributeModel {
        val stat = attributesRetriever.retrieveById(AttributeId(attributeId))

        return AttributeModel()
                .withName(stat.data.name)
                .withDescription(stat.data.description)
                .withIdentity(IdentityModel()
                        .withId(stat.identity.id.id)
                        .withVersion(stat.identity.version)
                        .withCreated(stat.identity.created)
                        .withUpdated(stat.identity.updated)
                )
    }

}