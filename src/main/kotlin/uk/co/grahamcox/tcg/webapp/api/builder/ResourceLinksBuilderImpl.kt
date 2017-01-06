package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.ResourceLinks

/**
 * Standard implementation of the Resource Links Builder
 * @param MID the ID of the Model
 * @param MDATA The Data of the Model
 */
class ResourceLinksBuilderImpl<in MID : Id, in MDATA>(
        val selfLinkBuilder: LinkBuilder<Model<MID, MDATA>>,
        val otherLinkBuilders: Map<String, LinkBuilder<Model<MID, MDATA>>>? = null
) : ResourceLinksBuilder<MID, MDATA> {
    /**
     * Build the Resource Links for the given Model
     * @param model The model to build the links for
     * @return the resource links
     */
    override fun buildLinks(model: Model<MID, MDATA>) = ResourceLinks(
            self = selfLinkBuilder.buildLink(model),
            otherLinks = otherLinkBuilders?.mapValues { it.value.buildLink(model) } ?: mapOf()
    )
}