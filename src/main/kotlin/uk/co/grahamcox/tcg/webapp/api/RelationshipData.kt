package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonUnwrapped

/**
 * Representation of the Data in a relationship
 * @property id The ID of the resource
 */
data class RelationshipData<out ID>(
        @JsonUnwrapped val id: ResourceIdentity<ID>
)