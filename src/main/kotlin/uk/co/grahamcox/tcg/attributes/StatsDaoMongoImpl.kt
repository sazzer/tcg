package uk.co.grahamcox.tcg.attributes

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import java.time.Clock

/**
 * Implementation of the Stats DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class StatsDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock) : BaseMongoDao<StatId, StatData>(db, "stat", clock) {

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: Document): Model<StatId, StatData> {
        return Model(
                identity = Identity(
                        id = StatId(result.getString("_id")),
                        version = result.getString("version"),
                        created = result.getDate("created").toInstant(),
                        updated = result.getDate("updated").toInstant()
                ),
                data = StatData(
                        name = result.getString("name"),
                        description = result.getString("description")
                )
        )
    }
}