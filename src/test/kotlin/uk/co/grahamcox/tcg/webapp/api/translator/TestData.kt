package uk.co.grahamcox.tcg.webapp.api.translator

import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.webapp.api.*
import java.net.URI
import java.time.Instant
import java.util.*

/**
 * Test data for the tests
 */
object TestData {
    /** The raw test ID */
    val rawId = UUID.randomUUID().toString()
    /** The test ID */
    val testId = TestId(rawId)
    /** The version */
    val version = UUID.randomUUID().toString()
    /** The current time */
    val now = Instant.now()
    /** The created time */
    val created = now.minusSeconds(3600)
    /** The updated time */
    val updated = now.minusSeconds(1800)
    /** the test data */
    val testData = UUID.randomUUID().toString()
    /** The test identity */
    val testIdentity = Identity(
            id = testId,
            version = version,
            created = created,
            updated = updated
    )
    /** The test model */
    val testModel = Model(
            identity = testIdentity,
            data = testData
    )

    /** The translated resource identity */
    val resourceIdentity = ResourceIdentity(
            type = "testData",
            id = rawId
    )
    /** The translated resource attributes */
    val resourceAttributes = UUID.randomUUID().toString()
    /** The translated resource links */
    val resourceLinks = ResourceLinks(
            self = URI("http://test.example.com")
    )
    /** The translated resource data */
    val resourceData = ResourceData(
            id = resourceIdentity,
            attributes = resourceAttributes,
            links = resourceLinks,
            relationships = mapOf()
    )
    /** The translated resource */
    val resource = Resource(
            data = resourceData
    )
}