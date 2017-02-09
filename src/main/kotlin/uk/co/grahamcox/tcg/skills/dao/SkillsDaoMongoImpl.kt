package uk.co.grahamcox.tcg.skills.dao

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.dao.BaseKMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.skills.SkillData
import uk.co.grahamcox.tcg.skills.SkillId
import uk.co.grahamcox.tcg.skills.SkillSort
import java.time.Clock

/**
 * Implementation of the Skills DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class SkillsDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : SkillsDao, BaseKMongoDao<SkillId, SkillData, SkillsMongoModel, NoFilter, SkillSort>(db,
        "skills",
        clock,
        SkillsMongoModel::class.java) {

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
    override fun parseResult(result: SkillsMongoModel): Model<SkillId, SkillData> {
        return Model(
                identity = Identity(
                        id = SkillId(result.id),
                        version = result.version,
                        created = result.created,
                        updated = result.updated
                ),
                data = SkillData(
                        name = result.name,
                        description = result.description,
                        defaultValue = result.default
                )
        )
    }
}