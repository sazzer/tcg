package uk.co.grahamcox.tcg.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * Interface representing a base MongoDB Model
 */
abstract class MongoModel {
    /** The ID of the model */
    @MongoId
    @JsonProperty("_id")
    var id: String? = null
    /** The version of the model */
    var version: String? = null
    /** When the model was created */
    var created: Instant? = null
    /** Whent he model was updated */
    var updated: Instant? = null
}