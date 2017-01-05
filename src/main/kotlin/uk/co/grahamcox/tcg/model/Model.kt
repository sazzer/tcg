package uk.co.grahamcox.tcg.model

/**
 * Representation of a single model object
 * @param ID The type to use for the ID
 * @param DATA The type to use for the model data
 * @property identity The identity of the model object
 * @property data The actual data for the model object
 */
data class Model<out ID: Id, out DATA>(
        val identity: Identity<ID>,
        val data: DATA
)