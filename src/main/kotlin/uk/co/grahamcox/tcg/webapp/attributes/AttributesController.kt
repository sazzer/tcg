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
        val attribute = attributesRetriever.retrieveById(AttributeId(attributeId))

        return AttributeModel()
                .withName(attribute.data.name)
                .withDescription(attribute.data.description)
                .withIdentity(IdentityModel()
                        .withId(attribute.identity.id.id)
                        .withVersion(attribute.identity.version)
                        .withCreated(attribute.identity.created)
                        .withUpdated(attribute.identity.updated)
                )
    }

}