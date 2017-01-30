package uk.co.grahamcox.tcg.genders

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.races.RaceId
import java.time.Clock

/**
 * Implementation of the Genders DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class GendersDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : GendersDao, BaseMongoDao<GenderId, GenderData, GenderFilter, GenderSort>(db, "genders", clock) {

    /**
     * The mapping of sort fields to the actual database field
     */
    override val sortFields = mapOf(
            GenderSort.NAME to "name",
            GenderSort.RACE to "race"
    )

    /**
     * The mapping of filter fields to the actual database field
     */
    override val filterFields = mapOf(
            GenderFilter.RACE to "race"
    )

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: Document): Model<GenderId, GenderData> {
        return Model(
                identity = Identity(
                        id = GenderId(result.getString("_id")),
                        version = result.getString("version"),
                        created = result.getDate("created").toInstant(),
                        updated = result.getDate("updated").toInstant()
                ),
                data = GenderData(
                        name = result.getString("name"),
                        description = result.getString("description"),
                        race = RaceId(result.getString("race"))
                )
        )
    }
}