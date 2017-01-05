package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Representation of a related resource for input purposes
 * @param RID The ID type of the related resource
 * @property type The type of the resource
 * @property data The data in the resource
 * @property data The relationship data, if any
 */
data class InputRelatedResource<out RID> @JsonCreator constructor(
        val type: String,
        val id: RID,
        val data: Map<String, Any>?
) : Relationship