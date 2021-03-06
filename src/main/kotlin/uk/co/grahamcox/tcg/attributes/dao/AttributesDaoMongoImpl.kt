package uk.co.grahamcox.tcg.attributes.dao

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.attributes.AttributeSort
import uk.co.grahamcox.tcg.dao.BaseKMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import java.time.Clock

/**
 * Implementation of the Attributes DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class AttributesDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : AttributesDao, BaseKMongoDao<AttributeId, AttributeData, AttributesMongoModel, NoFilter, AttributeSort>(db,
        "attributes",
        clock,
        AttributesMongoModel::class.java) {

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
    override fun parseResult(result: AttributesMongoModel): Model<AttributeId, AttributeData> {
        return Model(
                identity = Identity(
                        id = AttributeId(result.id),
                        version = result.version,
                        created = result.created,
                        updated = result.updated
                ),
                data = AttributeData(
                        name = result.name,
                        description = result.description,
                        defaultValue = result.default
                )
        )
    }
}