package uk.co.grahamcox.tcg.webapp.api.builder

import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import java.time.Instant
import java.util.*

object TestData {
    /** The current time */
    private val now = Instant.now()
    /** The Raw ID to use */
    val rawID = "abc"
    /** The ID to use */
    val id = TestId(rawID)
    /** The created time to use */
    val created = now.minusSeconds(3600)
    /** The updated time to use */
    val updated = now.minusSeconds(1800)
    /** The version to use */
    val version = UUID.randomUUID().toString()
    /** The Identity to use */
    val identity = Identity(
            id = id,
            created = created,
            updated = updated,
            version = version
    )
    /** The model data to use */
    val modelData = "Hello"
    /** The model to use */
    val model = Model(identity = identity, data = modelData)
}