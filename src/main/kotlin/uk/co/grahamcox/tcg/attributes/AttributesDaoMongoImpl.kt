package uk.co.grahamcox.tcg.attributes

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import java.time.Clock

/**
 * Implementation of the Attributes DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class AttributesDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock) : BaseMongoDao<AttributeId, AttributeData, AttributeSort>(db, "attributes", clock) {

    /**
     * The mapping of sort fields to the actual database field
     */
    override val sortFields = mapOf(
            AttributeSort.NAME to "name"
    )

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: Document): Model<AttributeId, AttributeData> {
        return Model(
                identity = Identity(
                        id = AttributeId(result.getString("_id")),
                        version = result.getString("version"),
                        created = result.getDate("created").toInstant(),
                        updated = result.getDate("updated").toInstant()
                ),
                data = AttributeData(
                        name = result.getString("name"),
                        description = result.getString("description")
                )
        )
    }
}