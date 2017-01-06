package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Id
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.Resource

/**
 * Standard implementation of the Resource Builder
 */
class ResourceBuilderImpl<in MID : Id, in MDATA, out RID, out RDATA>(
        private val identityBuilder: ResourceIdentityBuilder<MID, RID>,
        private val dataTranslator: ResourceDataTranslator<MID, MDATA, RDATA>,
        private val linksBuilder: ResourceLinksBuilder<MID, MDATA>,
        private val relationshipBuilders: Map<String, RelationshipBuilder<MID, MDATA>>?
) : ResourceBuilder<MID, MDATA, RID, RDATA> {
    /**
     * Build a Resource representation of the given Model data
     * @param input The input model to build the resource from
     * @return the API resource
     */
    override fun buildResource(input: Model<MID, MDATA>) = Resource(
            identity = identityBuilder.buildResourceIdentity(identity = input.identity),
            data = dataTranslator.translateResourceData(input),
            links = linksBuilder.buildLinks(input),
            related = relationshipBuilders?.mapValues { it.value.buildRelationship(input) },
            included = null
    )
}