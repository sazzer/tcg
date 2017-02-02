package uk.co.grahamcox.tcg.skills

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import java.time.Clock

/**
 * Implementation of the Skills DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class SkillsDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : SkillsDao, BaseMongoDao<SkillId, SkillData, NoFilter, SkillSort>(db, "skills", clock) {

    /**
     * The mapping of sort fields to the actual database field
     */
    override val sortFields = mapOf(
            SkillSort.NAME to "name"
    )

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: Document): Model<SkillId, SkillData> {
        return Model(
                identity = Identity(
                        id = SkillId(result.getString("_id")),
                        version = result.getString("version"),
                        created = result.getDate("created").toInstant(),
                        updated = result.getDate("updated").toInstant()
                ),
                data = SkillData(
                        name = result.getString("name"),
                        description = result.getString("description")
                )
        )
    }
}