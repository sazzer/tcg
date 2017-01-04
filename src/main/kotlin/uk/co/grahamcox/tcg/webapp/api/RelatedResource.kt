package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonUnwrapped

/**
 * Representation of a related resource
 * @param RID The ID type of the related resource
 * @param RDATA The data type of the relationship, if any
 * @property identity The identity of the related resource
 * @property links The links for the related resource
 * @property data The relationship data, if any
 */
data class RelatedResource<out RID, out RDATA>(
        @JsonUnwrapped val identity: ResourceIdentity<RID>,
        val links: RelatedLinks,
        val data: RDATA?
) : Relationship