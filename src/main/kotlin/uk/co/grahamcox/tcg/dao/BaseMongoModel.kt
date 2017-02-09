package uk.co.grahamcox.tcg.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.litote.kmongo.MongoId
import java.time.Instant

/**
 * Base class for Mongo Model instances
 */
abstract class BaseMongoModel {
    /** The ID of the record */
    @MongoId
    @JsonProperty("_id")
    lateinit var id: String
    /** The version of the record */
    lateinit var version: String
    /** When the record was created */
    lateinit var created: Instant
    /** When the record was last updated */
    lateinit var updated: Instant
}