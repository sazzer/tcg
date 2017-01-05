package uk.co.grahamcox.tcg.webapp.api

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Representation of a resource as input to a controller method
 * @property type The type of the resource
 * @property data The data in the resource
 * @property related The related resources
 */
data class InputResource<out DATA> @JsonCreator constructor(
        val type: String,
        val data: DATA,
        val related: Map<String, List<InputRelatedResource<*>>>?
)